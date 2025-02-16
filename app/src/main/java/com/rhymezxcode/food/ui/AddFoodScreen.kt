package com.rhymezxcode.food.ui

import CategoryDropdown
import TextFormField
import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.rhymezxcode.food.R
import com.rhymezxcode.food.ui.component.AddFoodButton
import com.rhymezxcode.food.ui.component.ImageButton
import com.rhymezxcode.food.ui.viewModel.CreateFoodViewModel
import com.rhymezxcode.food.util.Constants.COMPOSABLE_SPACER_HEIGHT
import com.rhymezxcode.food.util.Constants.FONT_SIZE
import com.rhymezxcode.food.util.Constants.VERTICAL_SPACER
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun AddFoodScreen(
    navController: NavHostController,
    createFoodViewModel: CreateFoodViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val cameraPermissionState = remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }
    val storagePermissionState = remember { mutableStateOf(checkStoragePermission(context)) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        cameraPermissionState.value = isGranted
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        storagePermissionState.value = isGranted
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        TopBar(navController = navController)
        Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))
        ImageSelection(
            createFoodViewModel = createFoodViewModel,
            cameraPermissionState = cameraPermissionState.value,
            storagePermissionState = storagePermissionState.value,
            cameraPermissionLauncher = cameraPermissionLauncher,
            storagePermissionLauncher = storagePermissionLauncher
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))
        FoodDetailsSection(createFoodViewModel = createFoodViewModel)
    }
}

private fun checkStoragePermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}


@Composable
fun TopBar(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
            )
        }
        Text(
            text = "Add new food",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun ImageSelection(
    createFoodViewModel: CreateFoodViewModel,
    cameraPermissionState: Boolean,
    storagePermissionState: Boolean,
    cameraPermissionLauncher: ActivityResultLauncher<String>,
    storagePermissionLauncher: ActivityResultLauncher<String>
) {
    val context = LocalContext.current
    var selectedImages by remember { mutableStateOf(mutableListOf<Uri>()) }

    val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            createFoodViewModel.currentPhotoUri.value?.let { uri ->
                val imageFile = File(context.cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    imageFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                selectedImages.add(uri)
                createFoodViewModel.addImage(imageFile)
            }
        }
    }

    val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty()) {
            val imageFiles = uris.mapNotNull { uri ->
                try {
                    val imageFile = File(context.cacheDir, "gallery_image_${System.currentTimeMillis()}.jpg")
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        imageFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }

                    // Convert the File to MultipartBody.Part
                    val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val part = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
                    part
                } catch (e: Exception) {
                    Log.e("GalleryError", "Error copying image: ${e.message}")
                    null
                }
            }

            selectedImages.addAll(uris)
            createFoodViewModel.updateImages(imageFiles)
        }
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ImageButton(
                icon = painterResource(id = R.drawable.ic_camera),
                text = "Take photo",
                onClick = {
                    Log.d("ImageSelection", "Camera Button Clicked, cameraPermissionState: $cameraPermissionState") // ADD THIS LOG
                    if (cameraPermissionState) {
                        Log.d("ImageSelection", "Camera Permission Granted, launching camera") // ADD THIS LOG
                        val fileUri = createImageUri(context)
                        createFoodViewModel.setCurrentPhotoUri(fileUri)
                        launcherCamera.launch(fileUri)
                    } else {
                        Log.d("ImageSelection", "Camera Permission Denied, requesting permission") // ADD THIS LOG
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
            )
            ImageButton(
                icon = painterResource(id = R.drawable.ic_cloud),
                text = "Upload",
                onClick = {
                    Log.d("ImageSelection", "Upload Button Clicked, storagePermissionState: $storagePermissionState") // ADD THIS LOG
                    if (storagePermissionState) {
                        Log.d("ImageSelection", "Storage Permission Granted, launching gallery") // ADD THIS LOG
                        launcherGallery.launch("image/*")
                    } else {
                        Log.d("ImageSelection", "Storage Permission Denied, requesting permission") // ADD THIS LOG
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                },
            )
        }

        if (selectedImages.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Selected Images:", style = MaterialTheme.typography.bodyMedium)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(selectedImages) { imageUri ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(Color.Gray)
                    )
                }
            }
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, "New Photo")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: Uri.EMPTY
}


@Composable
fun FoodDetailsSection(createFoodViewModel: CreateFoodViewModel) {
    val name = createFoodViewModel.name.collectAsState().value
    val description = createFoodViewModel.description.collectAsState().value
    val calories = createFoodViewModel.calories.collectAsState().value
    val tags = createFoodViewModel.tags.collectAsState().value
    val selectedCategory = createFoodViewModel.categoryId.collectAsState().value

    TextFormField(
        label = "Name",
        placeholder = "Enter food name",
        value = name,
        onValueChange = { createFoodViewModel.updateName(it) },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Description",
        placeholder = "Enter food description",
        value = description,
        onValueChange = { createFoodViewModel.updateDescription(it) },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    CategoryDropdown(
        selectedCategory = selectedCategory,
        expanded = remember { mutableStateOf(false) }.value,
        onExpandedChange = { },
        onCategorySelected = { createFoodViewModel.updateCategory(it) },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Calories",
        placeholder = "Enter number of calories",
        value = calories,
        onValueChange = { createFoodViewModel.updateCalories(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    Column {
        TextFormField(
            label = "Tags",
            placeholder = "Add a tag",
            value = tags.joinToString(", "),
            onValueChange = {
                val updatedTags = it.split(",").map { tag -> tag.trim() }
                createFoodViewModel.updateTags(updatedTags)
            },
        )
        Text(
            text = "Press enter once you've typed a tag.",
            fontSize = FONT_SIZE.sp,
            color = Color.Gray,
        )
    }
    Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))

    AddFoodButton(
        name = name,
        description = description,
        categoryId = selectedCategory,
        calories = calories,
        tags = tags,
        onAddFood = { createFoodViewModel.createFood() },
    )
}

