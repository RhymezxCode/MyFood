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
import javax.inject.Inject

@HiltViewModel
class UpdateFoodViewModel @Inject constructor(
    private val updateFoodRepository: UpdateFoodRepository
) : ViewModel() {

    private val _food = MutableStateFlow<String>("")
    val food: StateFlow<String> get() = _food.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    // Call to fetch data
    fun createFood(foodRequest: UpdateFoodRequest?) {
        // Show loading
        _isLoading.value = true
        _errorMessage.value = null

        // Example of a repository call to fetch data
        viewModelScope.launch {
            try {
                val foodData = updateFoodRepository.updateFood(
                    foodRequest
                )
                _food.value = foodData.message()
            } catch (e: Exception) {
                // Handle error
                _food.value = ""
                _errorMessage.value = "Error fetching foods: ${e.message}"
            } finally {
                // Hide loading
                _isLoading.value = false
            }
        }
    }
}
