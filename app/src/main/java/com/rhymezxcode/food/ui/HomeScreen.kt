package com.rhymezxcode.food.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhymezxcode.food.data.model.FoodItem

@Composable
fun HomeScreen() {
    Scaffold() { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            HeaderSection()
            SearchSection()
            CategorySection()
            AllFoodsSection()
        }
    }
}


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one), // Replace with your actual profile image
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Hey there, Lucy!", fontWeight = FontWeight.Bold)
                Text(text = "Are you excited to create a tasty dish today?", fontSize = 12.sp)
            }
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "Notifications")
        }
    }
}

@Composable
fun SearchSection() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = { Text("Search foods...") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F5F5), // Light gray background
            focusedIndicatorColor = Color.Transparent,  // Remove border
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CategorySection() {
    val categories = listOf("All", "Morning Feast", "Sunrise Meal", "Dawn Delicacies")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryButton(text = category)
        }
    }
}

@Composable
fun CategoryButton(text: String) {
    val isSelected = text == "All"
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF1E90FF) else Color(0xFFF5F5F5),
            contentColor = if (isSelected) Color.White else Color.Black
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun AllFoodsSection() {
    Text(
        text = "All Foods",
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold
    )

    val foodItems = listOf(
        FoodItem(
            id = 1,
            name = "Garlic Butter Shrimp Pasta",
            calories = 320,
            description = "Creamy hummus spread on whole grain toast topped with sliced cucumbers and radishes.",
            imageUrl = com.rhymezxcode.food.R.drawable.food_item_two, // Replace with your image resource
            tags = listOf("healthy", "vegetarian")
        ),
        FoodItem(
            id = 2,
            name = "Lemon Herb Chicken Fettuccine",
            calories = 250,
            description = "Savory lentil soup with diced vegetables and aromatic herbs.",
            imageUrl = com.rhymezxcode.food.R.drawable.food_item_two, // Replace with your image resource
            tags = listOf("healthy")
        )
    )

    LazyColumn {
        items(foodItems) { foodItem ->
            FoodItemCard(foodItem = foodItem)
        }
    }
}

@Composable
fun FoodItemCard(foodItem: FoodItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = foodItem.imageUrl),
                contentDescription = foodItem.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = foodItem.name, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_two), // Replace with a calorie icon resource
                        contentDescription = "Calories",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red // Set calorie icon color
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${foodItem.calories} Calories", fontSize = 12.sp)
                }

                Text(text = foodItem.description, fontSize = 12.sp)

                Row(modifier = Modifier.padding(top = 8.dp)) {
                    foodItem.tags.forEach { tag ->
                        Chip(text = tag)
                    }
                }
            }
        }
    }
}

@Composable
fun Chip(text: String) {
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


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
