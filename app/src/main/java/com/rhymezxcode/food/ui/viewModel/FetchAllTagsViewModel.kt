package com.rhymezxcode.food.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhymezxcode.food.data.model.tags.Tags
import com.rhymezxcode.food.data.repository.FetchAllTagsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchAllTagsViewModel @Inject constructor(
    private val fetchAllTagsRepository: FetchAllTagsRepository
) : ViewModel() {

    private val _tagList = MutableStateFlow<List<Tags>>(emptyList())
    val tagList: StateFlow<List<Tags>> get() = _tagList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()

    // Call to fetch data
    fun fetchTagsList() {
        // Show loading
        _isLoading.value = true
        _errorMessage.value = null

        // Example of a repository call to fetch data
        viewModelScope.launch {
            try {
                val tagData = fetchAllTagsRepository.getAllTags()
                _tagList.value = tagData.data?: emptyList()
            } catch (e: Exception) {
                // Handle error
                _tagList.value = emptyList()
                _errorMessage.value = "Error fetching foods: ${e.message}"
            } finally {
                // Hide loading
                _isLoading.value = false
            }
        }
    }
}
