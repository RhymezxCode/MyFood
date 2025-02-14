package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhymezxcode.food.util.Constants.LIGHT_GRAY_CHIP_BACKGROUND

@Composable
fun Chip(text: String) {
    Surface(
        modifier = Modifier.padding(end = 4.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(LIGHT_GRAY_CHIP_BACKGROUND),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 10.sp,
        )
    }
}
