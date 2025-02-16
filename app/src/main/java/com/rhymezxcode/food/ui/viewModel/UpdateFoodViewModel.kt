package com.rhymezxcode.food.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhymezxcode.food.data.model.updateFood.UpdateFoodRequest
import com.rhymezxcode.food.data.repository.UpdateFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateFoodViewModel @Inject constructor(
    private val updateFoodRepository: UpdateFoodRepository
) : ViewModel() {

    // UI States
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description.asStateFlow()

    private val _categoryId = MutableStateFlow("")
    val categoryId: StateFlow<String> get() = _categoryId.asStateFlow()

    private val _calories = MutableStateFlow("")
    val calories: StateFlow<String> get() = _calories.asStateFlow()

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> = _tags

    private val _images = MutableStateFlow<List<File>>(emptyList())
    val images: StateFlow<List<File>> get() = _images.asStateFlow()

    // UI loading and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> get() = _successMessage.asStateFlow()

    // Method to update food
    fun updateFood(updateFoodRequest: UpdateFoodRequest) {
        val parts = updateFoodRequest.toRequestBodyParts()

        // Show loading
        _isLoading.value = true
        _errorMessage.value = null
        _successMessage.value = null

        viewModelScope.launch {
            try {
                val response = updateFoodRepository.updateFood(
                    name = parts["name"]!!,
                    description = parts["description"]!!,
                    categoryId = parts["category_id"]!!,
                    calories = parts["calories"]!!,
                    tags = updateFoodRequest.toTagParts(),
                    images = updateFoodRequest.toImageParts()
                )

                if (response.isSuccessful) {
                    // ✅ Handle success
                    _successMessage.value = "Food updated successfully!"
                    clearForm() // Reset form inputs
                } else {
                    // ❌ Handle failure response
                    val errorResponse = response.errorBody()?.string() ?: "Failed to update food"
                    _errorMessage.value = errorResponse
                }
            } catch (e: Exception) {
                // ❌ Handle exceptions (e.g., network failure)
                _errorMessage.value = "Error creating food: ${e.message}"
            } finally {
                _isLoading.value = false // Hide loading state
            }
        }
    }

    // ✅ Clears form after success
    private fun clearForm() {
        _name.value = ""
        _description.value = ""
        _categoryId.value = ""
        _calories.value = ""
        _tags.value = emptyList()
        _images.value = emptyList()
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

    fun addTag(tag: String) {
        if (tag !in _tags.value) {
            _tags.value = _tags.value + tag
        }
    }

    fun removeTag(tag: String) {
        _tags.value = _tags.value - tag
    }

    fun updateImages(value: List<File>) {
        _images.value = value
    }
}
