package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ImageButton(
    icon: Painter,
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .height(80.dp)
            .width(160.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.DarkGray,
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(28.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, color = Color.DarkGray)
        }
    }
}
