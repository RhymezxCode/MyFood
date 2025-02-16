package com.rhymezxcode.food.ui

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.rhymezxcode.food.ui.component.CategoryDropdown
import com.rhymezxcode.food.ui.component.ImageButton
import com.rhymezxcode.food.ui.component.TagInputField
import com.rhymezxcode.food.ui.viewModel.CreateFoodViewModel
import com.rhymezxcode.food.ui.viewModel.FetchAllCategoriesViewModel
import com.rhymezxcode.food.ui.viewModel.FetchAllTagsViewModel
import com.rhymezxcode.food.ui.viewModel.UpdateFoodViewModel
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

    // Observe category and tag data
    val categoryList by fetchAllCategoriesViewModel.categoryList.collectAsStateWithLifecycle()
    val isCategoryLoading by fetchAllCategoriesViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessageForCategory by fetchAllCategoriesViewModel.errorMessage.collectAsStateWithLifecycle()

    val tagList by fetchAllTagsViewModel.tagList.collectAsStateWithLifecycle()
    val isTagLoading by fetchAllTagsViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessageForTag by fetchAllTagsViewModel.errorMessage.collectAsStateWithLifecycle()

    // Fetch data when screen loads
    LaunchedEffect(Unit) {
        Log.d("AddFoodScreen", "Fetching categories and tags...")
        fetchAllTagsViewModel.fetchTagsList()
        fetchAllCategoriesViewModel.fetchCategoriesList()
    }

    // Show errors if fetching fails
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
        TopBar(navController)
        Spacer(modifier = Modifier.height(16.dp))

        // Food Details Section with categories and tags
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
    selectedImages: List<File>,
    onImageSelected: (File) -> Unit,
    onImageRemoved: (File) -> Unit,
    cameraPermissionState: Boolean,
    storagePermissionState: Boolean,
    cameraPermissionLauncher: ActivityResultLauncher<String>,
    storagePermissionLauncher: ActivityResultLauncher<String>,
) {
    val context = LocalContext.current
    val cacheDir = context.cacheDir
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

// Camera Launcher
    val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        Log.d("CameraDebug", "Camera capture success: $success, Uri: $cameraUri")
        if (success && cameraUri != null) {
            try {
                val imageFile = File(cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
                context.contentResolver.openInputStream(cameraUri!!)?.use { input ->
                    imageFile.outputStream().use { output -> input.copyTo(output) }
                }
                Log.d("CameraDebug", "Image saved to: ${imageFile.absolutePath}")
                onImageSelected(imageFile) // Call the function to update ViewModel
            } catch (e: Exception) {
                Log.e("CameraError", "Error copying camera image: ${e.message}")
            }
        } else {
            Log.e("CameraDebug", "Failed to capture image or URI is null")
        }
    }

// Gallery Launcher
    val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        Log.d("GalleryDebug", "Selected images: ${uris.size} URIs: $uris")
        val imageFiles = uris.mapNotNull { uri ->
            try {
                val file = File(cacheDir, "gallery_image_${System.currentTimeMillis()}.jpg")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    file.outputStream().use { output -> input.copyTo(output) }
                }
                Log.d("GalleryDebug", "Image copied to: ${file.absolutePath}")
                file
            } catch (e: Exception) {
                Log.e("GalleryError", "Error copying gallery image: ${e.message}")
                null
            }
        }
        if (imageFiles.isEmpty()) {
            Log.e("GalleryDebug", "No valid images copied")
        }
        imageFiles.forEach {
            Log.d("GalleryDebug", "Calling onImageSelected with: ${it.absolutePath}")
            onImageSelected(it)
        }
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ImageButton(
                icon = painterResource(id = R.drawable.ic_camera),
                text = "Take Photo",
                onClick = {
                    Log.d("CameraDebug", "Camera button clicked")
                    Log.d("CameraDebug", "Camera Permission State: $cameraPermissionState")

                    if (cameraPermissionState) {
                        val file = File(cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
                        cameraUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

                        Log.d("CameraDebug", "Generated Camera URI: $cameraUri")

                        if (cameraUri != null) {
                            launcherCamera.launch(cameraUri!!)
                            Log.d("CameraDebug", "Camera launched successfully")
                        } else {
                            Log.e("CameraDebug", "Error: Camera URI is null!")
                        }
                    } else {
                        Log.d("CameraDebug", "Requesting camera permission")
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
            )

            ImageButton(
                icon = painterResource(id = R.drawable.ic_cloud),
                text = "Upload",
                onClick = {
                    Log.d("StorageDebug", "Gallery button clicked")
                    Log.d("StorageDebug", "Storage Permission State: $storagePermissionState")

                    if (storagePermissionState) {
                        launcherGallery.launch("image/*")
                        Log.d("StorageDebug", "Gallery picker launched")
                    } else {
                        Log.d("StorageDebug", "Requesting storage permission")
                        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                            Manifest.permission.READ_MEDIA_IMAGES
                        else
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        storagePermissionLauncher.launch(permission)
                    }
                },
            )
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

    // Observe state
    val name = createFoodViewModel.name.collectAsStateWithLifecycle()
    val description = createFoodViewModel.description.collectAsStateWithLifecycle()
    val calories = createFoodViewModel.calories.collectAsStateWithLifecycle()
    val tags = createFoodViewModel.tags.collectAsStateWithLifecycle()
    val selectedCategory = createFoodViewModel.categoryId.collectAsStateWithLifecycle()
    val images = createFoodViewModel.images.collectAsStateWithLifecycle()

    val successMessage by createFoodViewModel.successMessage.collectAsStateWithLifecycle()
    val errorMessage by createFoodViewModel.errorMessage.collectAsStateWithLifecycle()
    val isLoading by createFoodViewModel.isLoading.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }

    val cameraPermissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    val storagePermissionState = remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
            else
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        )
    }

   // Permission Launchers
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        cameraPermissionState.value = isGranted
        Log.d("CameraDebug", "Camera Permission Granted: $isGranted")
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        storagePermissionState.value = isGranted
        Log.d("StorageDebug", "Storage Permission Granted: $isGranted")
    }

    // Show toast messages for success/error
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

// **Image Selection Component**
    ImageSelection(
        selectedImages = images.value,
        onImageSelected = { newImage ->
            createFoodViewModel.updateImages(images.value + newImage)
        },
        onImageRemoved = { removedImage ->
            createFoodViewModel.updateImages(images.value - removedImage)
        },
        cameraPermissionState = cameraPermissionState.value,
        storagePermissionState = storagePermissionState.value,
        cameraPermissionLauncher = cameraPermissionLauncher,
        storagePermissionLauncher = storagePermissionLauncher,
    )

// **Display selected images in LazyRow**
    LazyRow(
        modifier = Modifier.padding(top = 16.dp) // Add padding if necessary
    ) {
        items(images.value) { file ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        createFoodViewModel.updateImages(images.value - file)
                    },
            ) {
                Image(
                    painter = rememberAsyncImagePainter(file),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Image",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .clickable { createFoodViewModel.updateImages(images.value - file) },
                    tint = Color.White,
                )
            }
        }
    }


    TextFormField(
        label = "Name",
        placeholder = "Enter food name",
        value = name.value,
        onValueChange = { createFoodViewModel.updateName(it) },
    )
    Spacer(modifier = Modifier.height(16.dp))

    TextFormField(
        label = "Description",
        placeholder = "Enter food description",
        value = description.value,
        onValueChange = { createFoodViewModel.updateDescription(it) },
    )
    Spacer(modifier = Modifier.height(16.dp))

    if (!isCategoryLoading && categoryList.isNotEmpty()) {
        CategoryDropdown(
            categoryList = categoryList,
            expanded = expanded,
            selectedCategoryId = selectedCategory.value,
            onExpandedChange = { expanded = it },
            onCategorySelected = { createFoodViewModel.updateCategory(it) },
        )
    } else if (isCategoryLoading) {
        CircularProgressIndicator()
    } else {
        Text(text = "No categories available", color = Color.Red)
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextFormField(
        label = "Calories",
        placeholder = "Enter number of calories",
        value = calories.value,
        onValueChange = { createFoodViewModel.updateCalories(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    Spacer(modifier = Modifier.height(16.dp))

    // **Fixed Tags Input**
    if (!isTagLoading && tagList.isNotEmpty()) {
        TagInputField(createFoodViewModel, fetchAllTagsViewModel)
    } else if (isTagLoading) {
        CircularProgressIndicator()
    } else {
        Text(text = "No tags available", color = Color.Red)
    }

    Spacer(modifier = Modifier.height(16.dp))

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


