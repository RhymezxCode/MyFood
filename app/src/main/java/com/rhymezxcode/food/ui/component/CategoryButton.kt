package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rhymezxcode.food.util.Constants.BLUE_PRIMARY
import com.rhymezxcode.food.util.Constants.BUTTON_HEIGHT
import com.rhymezxcode.food.util.Constants.LIGHT_GRAY_BACKGROUND

@Composable
fun CategoryButton(text: String) {
    val isSelected = text == "All"
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.height(BUTTON_HEIGHT.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(BLUE_PRIMARY) else Color(LIGHT_GRAY_BACKGROUND),
            contentColor = if (isSelected) Color.White else Color.Black,
        ),
    ) {
        Text(text = text)
    }
}
