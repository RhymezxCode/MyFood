package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rhymezxcode.food.util.Constants.BUTTON_HEIGHT
import com.rhymezxcode.food.util.Constants.CORNER_RADIUS
import com.rhymezxcode.food.util.Constants.PRIMARY_BLUE

@Composable
fun RemoveFromCollectionButton() {
    Button(
        onClick = { println("remove from collection and should remove the todo note") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(BUTTON_HEIGHT.dp),
        shape = RoundedCornerShape(CORNER_RADIUS.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(PRIMARY_BLUE), contentColor = Color.White),
    ) {
        Text(text = "Remove from collection")
    }
}
