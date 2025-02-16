package com.rhymezxcode.food.ui

import CategoryDropdown
import TextFormField
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.rhymezxcode.food.R
import com.rhymezxcode.food.data.model.categories.Categories
import com.rhymezxcode.food.data.model.createFood.CreateFoodRequest
import com.rhymezxcode.food.data.model.tags.Tags
import com.rhymezxcode.food.ui.component.AddFoodButton
import com.rhymezxcode.food.ui.component.ImageButton
import com.rhymezxcode.food.ui.component.TagInputField
import com.rhymezxcode.food.ui.viewModel.FetchAllCategoriesViewModel
import com.rhymezxcode.food.ui.viewModel.FetchAllTagsViewModel
import com.rhymezxcode.food.ui.viewModel.UpdateFoodViewModel
import com.rhymezxcode.food.util.Constants.COMPOSABLE_SPACER_HEIGHT
import com.rhymezxcode.food.util.Constants.VERTICAL_SPACER
import com.rhymezxcode.food.util.showToast
import java.io.File

@Composable
fun AddFoodScreen(
    navController: NavHostController,
    createFoodViewModel: CreateFoodViewModel = hiltViewModel(),
    updateFoodViewModel: UpdateFoodViewModel = hiltViewModel(),
    fetchAllCategoriesViewModel: FetchAllCategoriesViewModel = hiltViewModel(),
    fetchAllTagsViewModel: FetchAllTagsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val cameraPermissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }
    val storagePermissionState = remember { mutableStateOf(checkStoragePermission(context)) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        cameraPermissionState.value = isGranted
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        storagePermissionState.value = isGranted
    }

    val tagList by fetchAllTagsViewModel.tagList.collectAsStateWithLifecycle()
    val isTagLoading by fetchAllTagsViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessageForTag by fetchAllTagsViewModel.errorMessage.collectAsStateWithLifecycle()

    val categoryList by fetchAllCategoriesViewModel.categoryList.collectAsStateWithLifecycle()
    val isCategoryLoading by fetchAllCategoriesViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessageForCategory by fetchAllCategoriesViewModel.errorMessage.collectAsStateWithLifecycle()

    // Call when the screen is first loaded
    LaunchedEffect(Unit) {
        fetchAllTagsViewModel.fetchTagsList()
        fetchAllCategoriesViewModel.fetchCategoriesList()
    }

    LaunchedEffect(errorMessageForTag) {
        errorMessageForTag?.let {
            context.showToast(it)
        }
    }

    LaunchedEffect(errorMessageForCategory) {
        errorMessageForCategory?.let {
            context.showToast(it)
        }
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
            updateFoodViewModel = updateFoodViewModel,
            cameraPermissionState = cameraPermissionState.value,
            storagePermissionState = storagePermissionState.value,
            cameraPermissionLauncher = cameraPermissionLauncher,
            storagePermissionLauncher = storagePermissionLauncher,
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))
        FoodDetailsSection(
            createFoodViewModel = createFoodViewModel,
            fetchAllTagsViewModel = fetchAllTagsViewModel,
            isCategoryLoading = isCategoryLoading,
            categoryList = categoryList,
            isTagLoading = isTagLoading,
            tagList = tagList,
        )
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
    updateFoodViewModel: UpdateFoodViewModel,
    cameraPermissionState: Boolean,
    storagePermissionState: Boolean,
    cameraPermissionLauncher: ActivityResultLauncher<String>,
    storagePermissionLauncher: ActivityResultLauncher<String>,
) {
    val context = LocalContext.current
    val selectedImages = remember { mutableStateListOf<File>() }

    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // Camera Launcher
    val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && cameraImageUri != null) {
            val imageFile = File(context.cacheDir, cameraImageUri!!.lastPathSegment ?: "camera_image.jpg")
            context.contentResolver.openInputStream(cameraImageUri!!)?.use { input ->
                imageFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            selectedImages.add(imageFile)
            createFoodViewModel.updateImages(selectedImages.toList()) // Only update images
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
                    imageFile
                } catch (e: Exception) {
                    Log.e("GalleryError", "Error copying image: ${e.message}")
                    null
                }
            }

            selectedImages.addAll(imageFiles) // Add new images without replacing old ones
            createFoodViewModel.updateImages(selectedImages.toList()) // Only update images
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
                    Log.d("ImageSelection", "Camera Button Clicked, cameraPermissionState: $cameraPermissionState")
                    if (cameraPermissionState) {
                        Log.d("ImageSelection", "Camera Permission Granted, launching camera")
                        val imageFile = File(context.cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
                        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
                        cameraImageUri = uri
                        launcherCamera.launch(uri) // Launch camera with the URI
                    } else {
                        Log.d("ImageSelection", "Camera Permission Denied, requesting permission")
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
            )
            ImageButton(
                icon = painterResource(id = R.drawable.ic_cloud),
                text = "Upload",
                onClick = {
                    Log.d("ImageSelection", "Upload Button Clicked, storagePermissionState: $storagePermissionState")
                    if (storagePermissionState) {
                        Log.d("ImageSelection", "Storage Permission Granted, launching gallery")
                        launcherGallery.launch("image/*")
                    } else {
                        Log.d("ImageSelection", "Storage Permission Denied, requesting permission")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                },
            )
        }

        LazyRow {
            items(selectedImages) { file ->
                Image(
                    painter = rememberAsyncImagePainter(file),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectedImages.remove(file) } // Remove image on click
                )
            }
        }
    }
}

@Composable
fun FoodDetailsSection(
    createFoodViewModel: CreateFoodViewModel,
    fetchAllTagsViewModel: FetchAllTagsViewModel,
    isCategoryLoading: Boolean,
    categoryList: List<Categories>,
    isTagLoading: Boolean,
    tagList: List<Tags>,
) {
    val context = LocalContext.current
    val name = createFoodViewModel.name.collectAsStateWithLifecycle()
    val description = createFoodViewModel.description.collectAsStateWithLifecycle()
    val calories = createFoodViewModel.calories.collectAsStateWithLifecycle()
    val tags = createFoodViewModel.tags.collectAsStateWithLifecycle()
    val selectedCategory = createFoodViewModel.categoryId.collectAsStateWithLifecycle()
    val images = createFoodViewModel.images.collectAsStateWithLifecycle()

    val successMessage by createFoodViewModel.successMessage.collectAsStateWithLifecycle()
    val errorMessage by createFoodViewModel.errorMessage.collectAsStateWithLifecycle()
    val isLoading by createFoodViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(successMessage) {
        successMessage?.let {
            context.showToast(it)
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            context.showToast(it)
        }
    }

    TextFormField(
        label = "Name",
        placeholder = "Enter food name",
        value = name.value,
        onValueChange = { createFoodViewModel.updateName(it) },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Description",
        placeholder = "Enter food description",
        value = description.value,
        onValueChange = { createFoodViewModel.updateDescription(it) },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    if (!isCategoryLoading && categoryList.isNotEmpty()) {
        val allCategoriesList = categoryList.map { it.name ?: "" }
        CategoryDropdown(
            categoryList = allCategoriesList,
            selectedCategory = selectedCategory.value,
            expanded = remember { mutableStateOf(false) }.value,
            onExpandedChange = { },
            onCategorySelected = { createFoodViewModel.updateCategory(it) },
        )
    }

    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Calories",
        placeholder = "Enter number of calories",
        value = calories.value,
        onValueChange = { createFoodViewModel.updateCalories(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    Column {
        if(!isTagLoading && tagList.isNotEmpty()) {
            TagInputField(createFoodViewModel, fetchAllTagsViewModel)
        }
    }

    Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))

    AddFoodButton(
        name = name.value,
        description = description.value,
        categoryId = selectedCategory.value,
        calories = calories.value,
        tags = tags.value,
        enabled = !isLoading,
        onAddFood = {
            createFoodViewModel.createFood(
                createFoodRequest = CreateFoodRequest(
                    name = name.value,
                    description = description.value,
                    categoryId = selectedCategory.value,
                    calories = calories.value,
                    tags = tags.value,
                    images = images.value,
                ),
            )
        },
    )

}

