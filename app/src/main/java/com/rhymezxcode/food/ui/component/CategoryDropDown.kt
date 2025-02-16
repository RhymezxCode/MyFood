package com.rhymezxcode.food.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rhymezxcode.food.data.model.categories.Categories

@Composable
fun CategoryDropdown(
    categoryList: List<Categories>,
    selectedCategoryId: Int?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCategorySelected: (Int) -> Unit,
) {
    val selectedCategoryName = categoryList.find { it.id == selectedCategoryId }?.name ?: "Select Category"

    Log.d("CategoryDebug", "Category List: $categoryList")
    Log.d("CategoryDebug", "Selected Category ID: $selectedCategoryId")
    Log.d("CategoryDebug", "Selected Category Name: $selectedCategoryName")
    Log.d("CategoryDebug", "Dropdown Expanded: $expanded")

    Box {
        OutlinedTextField(
            value = selectedCategoryName,  // ✅ Show selected category name
            onValueChange = {},
            label = { Text("Category") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    Log.d("CategoryDebug", "Dropdown clicked, new state: ${!expanded}")
                    onExpandedChange(!expanded)
                }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                Log.d("CategoryDebug", "Dropdown dismissed")
                onExpandedChange(false)
            },
        ) {
            categoryList.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name ?: "") },
                    onClick = {
                        Log.d("CategoryDebug", "Category selected: ${category.id} - ${category.name}")
                        onCategorySelected(category.id ?: 0)  // ✅ Pass ID
                        onExpandedChange(false) // ✅ Close dropdown after selection
                    },
                )
            }
        }
    }
}


