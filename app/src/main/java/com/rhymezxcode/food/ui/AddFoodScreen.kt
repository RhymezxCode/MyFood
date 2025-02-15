package com.rhymezxcode.food.ui

import CategoryDropdown
import TextFormField
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rhymezxcode.food.R
import com.rhymezxcode.food.ui.component.AddFoodButton
import com.rhymezxcode.food.ui.component.ImageButton
import com.rhymezxcode.food.ui.viewModel.CreateFoodViewModel
import com.rhymezxcode.food.util.Constants.COMPOSABLE_SPACER_HEIGHT
import com.rhymezxcode.food.util.Constants.FONT_SIZE
import com.rhymezxcode.food.util.Constants.VERTICAL_SPACER
import okhttp3.MultipartBody
import java.io.File

@Composable
fun AddFoodScreen(
    navController: NavHostController,
    createFoodViewModel: CreateFoodViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        // Top Bar with navigation
        TopBar(navController = navController)

        Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

        // Image Selection UI
        ImageSelection(
            createFoodViewModel = createFoodViewModel,
        )

        Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))

        // Food details section (name, description, etc.)
        FoodDetailsSection(
            createFoodViewModel = createFoodViewModel,
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
fun ImageSelection(createFoodViewModel: CreateFoodViewModel) {
    val context = LocalContext.current

    val launcherCamera = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            createFoodViewModel.currentPhotoUri.value?.let { uri ->
                // Create a temporary file to save the image
                val imageFile = File(context.cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    imageFile.outputStream().use { output ->
                        input.copyTo(output) // Save the image to a file
                    }
                }

                // Update ViewModel with the image file
                createFoodViewModel.addImage(imageFile) // Add the image file directly
            }
        }
    }

    val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val imageFile = File(context.cacheDir, "gallery_image_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(it)?.use { input ->
                imageFile.outputStream().use { output ->
                    input.copyTo(output) // Save the image to a file
                }
            }
            createFoodViewModel.updateImages(mutableListOf(MultipartBody.Part.createFormData("images", imageFile.toString())) ) // Update ViewModel with the image
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ImageButton(
            icon = painterResource(id = R.drawable.ic_camera),
            text = "Take photo",
            onClick = {
                val fileUri = createImageUri(context) // Create URI to store the captured photo
                createFoodViewModel.setCurrentPhotoUri(fileUri) // Set the URI in the ViewModel
                launcherCamera.launch(fileUri)
            },
        )
        ImageButton(
            icon = painterResource(id = R.drawable.ic_cloud),
            text = "Upload",
            onClick = {
                launcherGallery.launch("image/*")
            },
        )
    }
}

// Helper function to create URI for camera photo
private fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, "New Photo")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: Uri.EMPTY
}


@Composable
fun FoodDetailsSection(createFoodViewModel: CreateFoodViewModel) {
    // Collecting the states from the ViewModel
    val name = createFoodViewModel.name.collectAsState().value
    val description = createFoodViewModel.description.collectAsState().value
    val calories = createFoodViewModel.calories.collectAsState().value
    val tags = createFoodViewModel.tags.collectAsState().value
    val selectedCategory = createFoodViewModel.categoryId.collectAsState().value

    // Setting the mutable state of UI to ViewModel's state
    TextFormField(
        label = "Name",
        placeholder = "Enter food name",
        value = name,
        onValueChange = { createFoodViewModel.updateName(it) }, // Update ViewModel on value change
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Description",
        placeholder = "Enter food description",
        value = description,
        onValueChange = { createFoodViewModel.updateDescription(it) }, // Update ViewModel on value change
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    CategoryDropdown(
        selectedCategory = selectedCategory,
        expanded = remember { mutableStateOf(false) }.value,  // Managing dropdown state locally
        onExpandedChange = { /* Handle expanded state */ },
        onCategorySelected = { createFoodViewModel.updateCategory(it) }, // Update ViewModel with selected category
    )

    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Calories",
        placeholder = "Enter number of calories",
        value = calories,
        onValueChange = { createFoodViewModel.updateCalories(it) }, // Update ViewModel on value change
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )

    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    Column {
        TextFormField(
            label = "Tags",
            placeholder = "Add a tag",
            value = tags.joinToString(", "),  // Display tags as comma separated string
            onValueChange = {
                val updatedTags = it.split(",").map { tag -> tag.trim() }
                createFoodViewModel.updateTags(updatedTags) // Update ViewModel with the tags list
            },
        )
        Text(
            text = "Press enter once you've typed a tag.",
            fontSize = FONT_SIZE.sp,
            color = Color.Gray,
        )
    }

    Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))

    // Add Food button that triggers the creation of the food
    AddFoodButton(
        name = name,
        description = description,
        categoryId = selectedCategory,
        calories = calories,
        tags = tags,
        onAddFood = { createFoodViewModel.createFood()},
    )
}


@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
//    AddFoodScreen()
}
