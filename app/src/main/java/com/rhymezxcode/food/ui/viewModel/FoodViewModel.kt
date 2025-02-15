package com.rhymezxcode.food.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhymezxcode.food.data.model.FoodItem
import com.rhymezxcode.food.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _foodList = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodList: StateFlow<List<FoodItem>> get() = _foodList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    // Call to fetch data
    fun fetchFoodList() {
        // Show loading
        _isLoading.value = true
        _errorMessage.value = null

        // Example of a repository call to fetch data
        viewModelScope.launch {
            try {
                val foodData = foodRepository.fetchAllFoods()
                _foodList.value = foodData
            } catch (e: Exception) {
                // Handle error
                _foodList.value = emptyList()
                _errorMessage.value = "Error fetching foods: ${e.message}"
            } finally {
                // Hide loading
                _isLoading.value = false
            }
        }
    }
}
