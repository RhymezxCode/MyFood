package com.rhymezxcode.food.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.ui.component.Chip
import com.rhymezxcode.food.ui.component.RemoveFromCollectionButton
import com.rhymezxcode.food.util.Constants.CALORIES
import com.rhymezxcode.food.util.Constants.CALORIES_ICON_SIZE
import com.rhymezxcode.food.util.Constants.FONT_SIZE
import com.rhymezxcode.food.util.Constants.IMAGE_HEIGHT
import com.rhymezxcode.food.util.Constants.NOTES_FONT_SIZE
import com.rhymezxcode.food.util.Constants.PRIMARY_BLUE
import com.rhymezxcode.food.util.Constants.SPACER_WIDTH
import com.rhymezxcode.food.util.safeNavigateUp
import com.rhymezxcode.food.util.showToast

@Composable
fun ShowFoodScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
    val navController = rememberNavController()
    val context: Context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.safeNavigateUp() }) {
            Icon(
                painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_back),
                contentDescription = "Back",
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { context.showToast("Favorite Clicked") }) {
                Icon(
                    painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_love),
                    contentDescription = "Favorite",
                )
            }
            IconButton(onClick = { context.showToast("Edit Clicked") }) {
                Icon(
                    painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_edit),
                    contentDescription = "Edit",
                )
            }
        }
    }
}

@Composable
fun ImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IMAGE_HEIGHT.dp),
    ) {
        Image(
            painter = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one),
            contentDescription = "Food Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)) // Adds corner radius
                .background(Color.White)
                .padding(4.dp),
        ) {
            Text(
                text = "1/10",
                color = Color.Black,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun DetailsSection() {
    Column(
        modifier = Modifier
            .padding(16.dp),
    ) {
        Text(
            text = "Garlic Butter Shrimp Pasta",
            style = MaterialTheme.typography.headlineSmall,
        )

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Chip(text = "healthy")
            Chip(text = "vegetarian")
        }

        Text(
            text = "Creamy hummus spread on whole grain toast topped with sliced cucumbers and radishes, " +
                "creating a delightful blend of textures and flavors. The smoothness of the hummus complements " +
                "the crunchiness of the cucumbers, while the radishes add a peppery bite that elevates the dish. " +
                "To enhance this already delicious combination, consider adding a sprinkle of paprika for a " +
                "touch of warmth, " +
                "or a drizzle of olive oil to enrich the flavors further. You could also incorporate some " +
                "fresh herbs, " +
                "like dill or parsley, to introduce a burst of freshness.",
            modifier = Modifier.padding(top = 16.dp),
            fontSize = FONT_SIZE.sp,
            color = Color.Gray,
        )

        NutritionInfo()

        NotesSection()
    }
}

@Composable
fun NutritionInfo() {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_calories),
            contentDescription = "Calories",
            modifier = Modifier.size(CALORIES_ICON_SIZE.dp),
            tint = Color.Red,
        )
        Spacer(modifier = Modifier.width(SPACER_WIDTH.dp))
        Text(text = "$CALORIES Calories", fontSize = FONT_SIZE.sp)
    }
}

@Composable
fun NotesSection() {
    Text(text = "Notes", modifier = Modifier.padding(top = 16.dp), fontWeight = FontWeight.Bold)
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable { println("this is a clickable action and should remove the todo note") },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = com.rhymezxcode.food.R.drawable.ic_note),
            contentDescription = "Add Notes",
            tint = Color(PRIMARY_BLUE)
                .also { println("this is the tint: $it it has to be int for it to work") },
        )
        Spacer(modifier = Modifier.width(SPACER_WIDTH.dp))
        Text(text = "Add notes", color = Color(PRIMARY_BLUE), fontSize = NOTES_FONT_SIZE.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ShowFoodScreenPreview() {
    ShowFoodScreen()
}
