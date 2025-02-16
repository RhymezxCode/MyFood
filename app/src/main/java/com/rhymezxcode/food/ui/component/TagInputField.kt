package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rhymezxcode.food.ui.viewModel.FetchAllTagsViewModel


@Composable
fun TagInputField(createFoodViewModel: CreateFoodViewModel, fetchAllTagsViewModel: FetchAllTagsViewModel) {
    var text by remember { mutableStateOf("") }
    val tags by fetchAllTagsViewModel.tagList.collectAsStateWithLifecycle()

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Tags") },
            placeholder = { Text("Add a tag") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (text.isNotBlank()) {
                        createFoodViewModel.addTag(text.trim())  // Add tag to ViewModel
                        text = ""  // Clear input
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display the selected tags as chips
        LazyRow {
            items(tags) { tag ->
                CustomTagChip(
                    label = tag.toString(),
                    onRemove = { createFoodViewModel.removeTag(tag.toString()) }
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Press enter once you've typed a tag.",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
