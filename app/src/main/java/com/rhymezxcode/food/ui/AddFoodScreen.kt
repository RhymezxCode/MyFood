@file:Suppress("DEPRECATION")

package com.rhymezxcode.food.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddFoodScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        ImageSelection()
        Spacer(modifier = Modifier.height(24.dp))
        FoodDetailsSection()
        Spacer(modifier = Modifier.height(24.dp))
        AddFoodButton()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO: Navigate back*/ }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(text = "Add new food", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ImageSelection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ImageButton(
            icon = Icons.Filled.CameraAlt,
            text = "Take photo",
            onClick = { /*TODO: Launch camera*/ }
        )
        ImageButton(
            icon = Icons.Filled.CloudUpload,
            text = "Upload",
            onClick = { /*TODO: Open file picker*/ }
        )
    }
}

@Composable
fun ImageButton(icon: Any, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(160.dp)
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (icon) {
                is androidx.compose.ui.graphics.vector.ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier.size(24.dp)
                )

                else -> {}
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun FoodDetailsSection() {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Dawn Delicacies") }
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Dawn Delicacies", "Breakfast", "Lunch", "Dinner", "Snack")


    TextFormField(label = "Name", placeholder = "Enter food name", value = name, onValueChange = { name = it })
    Spacer(modifier = Modifier.height(8.dp))
    TextFormField(label = "Description", placeholder = "Enter food description", value = description, onValueChange = { description = it })
    Spacer(modifier = Modifier.height(8.dp))

    Column {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        selectedCategory = category
                        expanded = false
                    })
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
    TextFormField(label = "Calories", placeholder = "Enter number of calories", value = calories, onValueChange = { calories = it },
        keyboardType = KeyboardType.Number)
    Spacer(modifier = Modifier.height(8.dp))
    Column {
        TextFormField(label = "Tags", placeholder = "Add a tag", value = tag, onValueChange = { tag = it })
        Text(text = "Press enter once you've typed a tag.", fontSize = 10.sp, color = Color.Gray)
    }
}

@Composable
fun TextFormField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray
        )
    )
}

@Composable
fun AddFoodButton() {
    Button(
        onClick = { /*TODO: Add food to data source*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F0FE), contentColor = Color(0xFF1A73E8))
    ) {
        Text(text = "Add food")
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    AddFoodScreen()
}
