package com.rhymezxcode.food.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhymezxcode.food.data.model.fetchOneFoodModel.OneFoodItem
import com.rhymezxcode.food.data.repository.FetchOneFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchOneFoodViewModel @Inject constructor(
    private val fetchOneFoodRepository: FetchOneFoodRepository
) : ViewModel() {

    private val _food = MutableStateFlow<OneFoodItem?>(null) // Or your FoodItem type
    val food: StateFlow<OneFoodItem?> = _food.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchOneFood(foodId: Int) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = fetchOneFoodRepository.fetchOneFood(foodId)
                _food.value = response.data
            } catch (e: Exception) {
                _food.value = null
                _errorMessage.value = "Error fetching food: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

