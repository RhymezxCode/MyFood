package com.rhymezxcode.food.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.rhymezxcode.food.data.model.fetchAllFoodModel.FoodItem
import com.rhymezxcode.food.util.Constants.CALORIES_ICON_SIZE
import com.rhymezxcode.food.util.Constants.CALORIES_TEXT_SIZE
import com.rhymezxcode.food.util.Constants.CARD_ELEVATION
import com.rhymezxcode.food.util.Constants.CARD_IMAGE_HEIGHT
import com.rhymezxcode.food.util.Constants.SPACER_SIZE
import com.rhymezxcode.food.util.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCard(foodItem: FoodItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION.dp),
        onClick = {
            navController.navigate(route = Route.ShowFood.showFoodPath(foodItem.id))
        }
    ) {
        Column {
            AsyncImage(
                model = foodItem.foodImages?.get(0)?.imageUrl,
                contentDescription = foodItem.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CARD_IMAGE_HEIGHT.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one),
                error = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "${foodItem.name}", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_calories),
                        contentDescription = "Calories",
                        modifier = Modifier.size(CALORIES_ICON_SIZE.dp),
                        tint = Color.Red,
                    )
                    Spacer(modifier = Modifier.width(SPACER_SIZE.dp))
                    Text(text = "${foodItem.calories} Calories", fontSize = CALORIES_TEXT_SIZE.sp)
                }

                Text(text = "${foodItem.description}", fontSize = 12.sp)

                Row(modifier = Modifier.padding(top = 8.dp)) {
                    foodItem.foodTags?.forEach { tag ->
                        Chip(text = tag)
                    }
                }
            }
        }
    }
}
