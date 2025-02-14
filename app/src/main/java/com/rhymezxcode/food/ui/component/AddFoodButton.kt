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
fun AddFoodButton() {
    Button(
        onClick = { addFood() },
        modifier = Modifier
            .fillMaxWidth()
            .height(ELEVATION.dp),
        shape = RoundedCornerShape(BUTTON_CORNER_RADIUS.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(BUTTON_ALPHA),
            contentColor = Color(BUTTON_COLOR),
        ),
    ) {
        Text(text = "Add food")
    }
}
