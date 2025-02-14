package com.rhymezxcode.food.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowFoodScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBarSection()
        ImageSection()
        DetailsSection()
        Spacer(modifier = Modifier.weight(1f)) // Push button to bottom
        RemoveFromCollectionButton()
    }
}

@Composable
fun TopAppBarSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO: Navigate back*/ }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO: Toggle favorite*/ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.Red)
            }
            IconButton(onClick = { /*TODO: Edit*/ }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
            }
        }
    }
}

@Composable
fun ImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one), // Replace with your image resource
            contentDescription = "Food Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "1/10",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(4.dp),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
fun DetailsSection() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = "Garlic Butter Shrimp Pasta", style = MaterialTheme.typography.headlineSmall) //h6 is deprecated, changed to headlineSmall

        Row(modifier = Modifier.padding(top = 8.dp)) {
            CustomChip(text = "healthy")
            CustomChip(text = "vegetarian")
        }

        Text(
            text = "Creamy hummus spread on whole grain toast topped with sliced cucumbers and radishes, creating a delightful blend of textures and flavors. The smoothness of the hummus complements the crunchiness of the cucumbers, while the radishes add a peppery bite that elevates the dish. To enhance this already delicious combination, consider adding a sprinkle of paprika for a touch of warmth, or a drizzle of olive oil to enrich the flavors further. You could also incorporate some fresh herbs, like dill or parsley, to introduce a burst of freshness.",
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 14.sp,
            color = Color.Gray
        )

        NutritionInfo()

        NotesSection()
    }
}

@Composable
fun CustomChip(text: String) {
    Surface(
        modifier = Modifier.padding(end = 4.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE0E0E0) // Light gray chip background
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 10.sp
        )
    }
}

@Composable
fun NutritionInfo() {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_calories), // Replace with calorie icon resource
            contentDescription = "Calories",
            modifier = Modifier.size(16.dp),
            tint = Color.Red
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "320 Calories", fontSize = 14.sp)
    }
}

@Composable
fun NotesSection() {
    Text(text = "Notes", modifier = Modifier.padding(top = 16.dp), fontWeight = FontWeight.Bold)
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable { /*TODO: Add notes*/ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_calories), contentDescription = "Add Notes", tint = Color(0xFF1E90FF))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Add notes", color = Color(0xFF1E90FF), fontSize = 14.sp)
    }
}

@Composable
fun RemoveFromCollectionButton() {
    Button(
        onClick = { /*TODO: Remove from collection*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E90FF), contentColor = Color.White)
    ) {
        Text(text = "Remove from collection")
    }
}

@Preview(showBackground = true)
@Composable
fun ShowFoodScreenPreview() {
    ShowFoodScreen()
}
