package com.rhymezxcode.food.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhymezxcode.food.data.model.categories.Categories
import com.rhymezxcode.food.data.repository.FetchAllCategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchAllCategoriesViewModel @Inject constructor(
    private val fetchAllCategoriesRepository: FetchAllCategoriesRepository
) : ViewModel() {

    private val _categoryList = MutableStateFlow<List<Categories>>(emptyList())
    val categoryList: StateFlow<List<Categories>> get() = _categoryList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    // Call to fetch data
    fun fetchCategoriesList() {
        // Show loading
        _isLoading.value = true
        _errorMessage.value = null

        // Example of a repository call to fetch data
        viewModelScope.launch {
            try {
                val categoryData = fetchAllCategoriesRepository.getAllCategories()
                _categoryList.value = categoryData.data?: emptyList()
            } catch (e: Exception) {
                // Handle error
                _categoryList.value = emptyList()
                _errorMessage.value = "Error fetching foods: ${e.message}"
            } finally {
                // Hide loading
                _isLoading.value = false
            }
        }
    }
}
