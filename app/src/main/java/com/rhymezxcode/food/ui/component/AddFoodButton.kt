package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rhymezxcode.food.util.Constants.BUTTON_ALPHA
import com.rhymezxcode.food.util.Constants.BUTTON_COLOR
import com.rhymezxcode.food.util.Constants.BUTTON_CORNER_RADIUS
import com.rhymezxcode.food.util.Constants.ELEVATION
import com.rhymezxcode.food.util.addFood

@Composable
fun AddFoodButton(
    name: String,
    description: String,
    categoryId: Int,
    calories: String,
    tags: List<Int>,
    enabled: Boolean,
    onAddFood: () -> Unit // Function to handle food addition
) {
    val isEnabled = name.isNotBlank() &&
        description.isNotBlank() &&
        categoryId.toString().isNotBlank() &&
        calories.isNotBlank() &&
        tags.isNotEmpty()

    Button(
        onClick = { onAddFood() },
        modifier = Modifier
            .fillMaxWidth()
            .height(ELEVATION.dp),
        shape = RoundedCornerShape(BUTTON_CORNER_RADIUS.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(BUTTON_ALPHA),
            contentColor = Color(BUTTON_COLOR),
        ),
        enabled = isEnabled || enabled // Enable only when all fields are filled
    ) {
        Text(text = "Add food")
    }
}
