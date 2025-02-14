package com.rhymezxcode.food.ui

import CategoryDropdown
import TextFormField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.R
import com.rhymezxcode.food.ui.component.AddFoodButton
import com.rhymezxcode.food.ui.component.ImageButton
import com.rhymezxcode.food.util.Constants.CATEGORY_DEFAULT
import com.rhymezxcode.food.util.Constants.COMPOSABLE_SPACER_HEIGHT
import com.rhymezxcode.food.util.Constants.FONT_SIZE
import com.rhymezxcode.food.util.Constants.VERTICAL_SPACER
import com.rhymezxcode.food.util.launchCamera
import com.rhymezxcode.food.util.openFilePicker

@Composable
fun AddFoodScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))
        ImageSelection()
        Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))
        FoodDetailsSection()
        Spacer(modifier = Modifier.height(VERTICAL_SPACER.dp))
        AddFoodButton()
    }
}

@Composable
fun TopBar() {
    val navController = rememberNavController()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
            )
        }
        Text(
            text = "Add new food",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun ImageSelection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ImageButton(
            icon = painterResource(id = com.rhymezxcode.food.R.drawable.ic_camera),
            text = "Take photo",
            onClick = { launchCamera() },
        )
        ImageButton(
            icon = painterResource(id = com.rhymezxcode.food.R.drawable.ic_cloud),
            text = "Upload",
            onClick = { openFilePicker() },
        )
    }
}

@Composable
fun FoodDetailsSection() {
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val calories = remember { mutableStateOf("") }
    val tag = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf(CATEGORY_DEFAULT) }
    val expanded = remember { mutableStateOf(false) }

    TextFormField(
        label = "Name",
        placeholder = "Enter food name",
        value = name.value,
        onValueChange = { name.value = it },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Description",
        placeholder = "Enter food description",
        value = description.value,
        onValueChange = { description.value = it },
    )
    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    CategoryDropdown(
        selectedCategory = selectedCategory.value,
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
        onCategorySelected = { selectedCategory.value = it },
    )

    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    TextFormField(
        label = "Calories",
        placeholder = "Enter number of calories",
        value = calories.value,
        onValueChange = { calories.value = it },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )

    Spacer(modifier = Modifier.height(COMPOSABLE_SPACER_HEIGHT.dp))

    Column {
        TextFormField(
            label = "Tags",
            placeholder = "Add a tag",
            value = tag.value,
            onValueChange = { tag.value = it },
        )
        Text(
            text = "Press enter once you've typed a tag.",
            fontSize = FONT_SIZE.sp,
            color = Color.Gray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    AddFoodScreen()
}
