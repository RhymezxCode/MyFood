package com.rhymezxcode.food.ui.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhymezxcode.food.data.model.createFood.CreateFoodRequest
import com.rhymezxcode.food.data.repository.CreateFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateFoodViewModel @Inject constructor(
    private val createFoodRepository: CreateFoodRepository
) : ViewModel() {

    // UI States to store user input
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description.asStateFlow()

    private val _categoryId = MutableStateFlow("")
    val categoryId: StateFlow<String> get() = _categoryId.asStateFlow()

    private val _calories = MutableStateFlow("")
    val calories: StateFlow<String> get() = _calories.asStateFlow()

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> get() = _tags.asStateFlow()

    private val _images = MutableStateFlow<List<MultipartBody.Part>>(emptyList())
    val images: StateFlow<List<MultipartBody.Part>> get() = _images.asStateFlow()

    // UI loading and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    // State for storing the URI of the current photo
    val currentPhotoUri = mutableStateOf<Uri?>(null)

    // Other ViewModel states and methods...

    fun setCurrentPhotoUri(uri: Uri) {
        currentPhotoUri.value = uri
    }

    // Function to create MultipartBody.Part for tags
    fun createTagsParts(tags: List<String>?): List<MultipartBody.Part>? {
        return tags?.map { tag ->
            val tagBody = tag.toRequestBody("text/plain".toMediaTypeOrNull()) // Create request body for each tag
            MultipartBody.Part.createFormData("tags", tag, tagBody)
        }
    }

    // Method to trigger food creation
    fun createFood() {
        // Access the current value of tags (StateFlow value)
        val currentTags = _tags.value

        // Prepare data from UI state
        val foodRequest = CreateFoodRequest(
            name = createRequestBody(_name.value),
            description = createRequestBody(_description.value),
            categoryId = createRequestBody(_categoryId.value),
            calories = createRequestBody(_calories.value),
            tags = createTagsParts(currentTags.takeIf { it.isNotEmpty() } ?: emptyList()), // Convert tags to MultipartBody.Part
            images = _images.value
        )

        // Show loading
        _isLoading.value = true
        _errorMessage.value = null

        // Perform the API call to create food
        viewModelScope.launch {
            try {
                val response = createFoodRepository.createFood(foodRequest)
                if (response.isSuccessful) {
                    // Handle success (response.data or whatever is returned)
                } else {
                    // Handle failure
                    _errorMessage.value = "Failed to create food"
                }
            } catch (e: Exception) {
                // Handle exception
                _errorMessage.value = "Error creating food: ${e.message}"
            } finally {
                // Hide loading
                _isLoading.value = false
            }
        }
    }

    // Helper function to create RequestBody
    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    // Functions to update values in the UI
    fun updateName(value: String) {
        _name.value = value
    }

    fun updateDescription(value: String) {
        _description.value = value
    }

    fun updateCategory(value: String) {
        _categoryId.value = value
    }

    fun updateCalories(value: String) {
        _calories.value = value
    }

    fun updateTags(value: List<String>) {
        _tags.value = value
    }

    fun updateImages(value: List<MultipartBody.Part>) {
        _images.value = value
    }

    // Updated addImage function to convert File to MultipartBody.Part before adding
    fun addImage(imageFile: File) {
        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val part = MultipartBody.Part.createFormData("images", imageFile.name, requestBody)
        val updatedList = _images.value.toMutableList()
        updatedList.add(part) // Add the image as MultipartBody.Part
        _images.value = updatedList // Update the image list
    }
}
