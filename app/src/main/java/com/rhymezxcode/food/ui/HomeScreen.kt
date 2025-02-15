package com.rhymezxcode.food.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rhymezxcode.food.R
import com.rhymezxcode.food.data.model.FoodItem
import com.rhymezxcode.food.ui.component.CategoryButton
import com.rhymezxcode.food.ui.component.FoodItemCard
import com.rhymezxcode.food.ui.viewModel.FoodViewModel
import com.rhymezxcode.food.util.Constants.AVATAR_SIZE
import com.rhymezxcode.food.util.Constants.LIGHT_GRAY_BACKGROUND
import com.rhymezxcode.food.util.Constants.SPACER_SIZE


@Composable
fun HomeScreen(viewModel: FoodViewModel = hiltViewModel()) {
    val foodList by viewModel.foodList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    // Call fetchFoodList when the screen is first loaded
    LaunchedEffect(Unit) {
        viewModel.fetchFoodList()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
        ) {
            HeaderSection()
            SearchSection()
            CategorySection()

            // Display the error message if it's not null
            if (errorMessage != null) {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            AllFoodsSection(foodList, isLoading)
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
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_avatar), // Use R.drawable here
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(AVATAR_SIZE.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(SPACER_SIZE.dp))
            Column {
                Text(text = "Hey there, Lucy!", fontWeight = FontWeight.Bold)
                Text(text = "Are you excited to create a tasty dish today?", fontSize = 12.sp)
            }
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification), // Use R.drawable here
                contentDescription = "Notifications",
            )
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
            unfocusedContainerColor = Color(LIGHT_GRAY_BACKGROUND),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}

@Composable
fun CategorySection() {
    val categories = listOf("All", "Morning Feast", "Sunrise Meal", "Dawn Delicacies")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories) { category ->
            CategoryButton(text = category)
        }
    }
}

@Composable
fun AllFoodsSection(foodList: List<FoodItem>, isLoading: Boolean) {
    Text(
        text = "All Foods",
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
    )

    if (isLoading) {
        // Show a progress indicator while loading
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (foodList.isEmpty()) {
        // Show message if no food found
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No foods available", color = Color.Gray)
        }
    } else {
        LazyColumn {
            items(foodList) { foodItem ->
                FoodItemCard(foodItem = foodItem)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
     HomeScreen()
}
