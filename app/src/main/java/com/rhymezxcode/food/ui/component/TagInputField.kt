package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rhymezxcode.food.ui.viewModel.CreateFoodViewModel
import com.rhymezxcode.food.ui.viewModel.FetchAllTagsViewModel


@Composable
fun TagInputField(createFoodViewModel: CreateFoodViewModel, fetchAllTagsViewModel: FetchAllTagsViewModel) {
    var text by remember { mutableStateOf("") }
    val tags by fetchAllTagsViewModel.tagList.collectAsStateWithLifecycle()
    val selectedTags by createFoodViewModel.tags.collectAsStateWithLifecycle() // Track selected tags
    val allSelectedTags = tags.filter { it.id in selectedTags }.map { it.name } // Filter tags based on selected tags

    Column {
        OutlinedTextField(
            value = allSelectedTags.joinToString(), // Display selected tags
            onValueChange = { text = it },
            label = { Text("Tags") },
            placeholder = { Text("Add a tag") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display available tags as selectable chips
        LazyRow {
            items(tags) { tag ->
                CustomTagChip(
                    label = tag.name.toString(),
                    onClick = { createFoodViewModel.addTag(tag.id?:0) }, // Add tag on click
                    onRemove = { createFoodViewModel.removeTag(tag.id?:0) }
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Press enter once you've typed a tag or click a chip to add.",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
