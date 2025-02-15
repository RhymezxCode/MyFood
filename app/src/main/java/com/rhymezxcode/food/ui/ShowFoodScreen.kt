package com.rhymezxcode.food.ui

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.rhymezxcode.food.data.model.fetchOneFoodModel.FoodImage
import com.rhymezxcode.food.data.model.fetchOneFoodModel.OneFoodItem
import com.rhymezxcode.food.ui.component.Chip
import com.rhymezxcode.food.ui.component.RemoveFromCollectionButton
import com.rhymezxcode.food.ui.viewModel.FetchOneFoodViewModel
import com.rhymezxcode.food.util.Constants.CALORIES_ICON_SIZE
import com.rhymezxcode.food.util.Constants.FONT_SIZE
import com.rhymezxcode.food.util.Constants.IMAGE_HEIGHT
import com.rhymezxcode.food.util.Constants.NOTES_FONT_SIZE
import com.rhymezxcode.food.util.Constants.PRIMARY_BLUE
import com.rhymezxcode.food.util.Constants.SPACER_WIDTH
import com.rhymezxcode.food.util.showToast

@Composable
fun ShowFoodScreen(viewModel: FetchOneFoodViewModel = hiltViewModel(), foodId: Int, navController: NavHostController) {
    val food by viewModel.food.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchOneFood(foodId)
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage?:"", color = Color.Red)
            }
        }

        food != null -> {
            FoodDetailsScreen(food, navController)
        }
    }
}


@Composable
fun FoodDetailsScreen(food: OneFoodItem?, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopAppBarSection(navController)
        ImageSection(food?.foodImages)
        DetailsSection(food)
        Spacer(modifier = Modifier.weight(1f))
        RemoveFromCollectionButton()
    }
}


@Composable
fun TopAppBarSection(navController: NavHostController) {
    val context: Context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
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
fun ImageSection(foodImageUrl: List<FoodImage>?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IMAGE_HEIGHT.dp),
    ) {
        if (foodImageUrl != null && foodImageUrl.isNotEmpty()) {
            val listState = rememberLazyListState()
            var currentImageIndex by remember { mutableIntStateOf(0) }

            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
                    currentImageIndex = index
                }
            }

            LazyRow(modifier = Modifier.fillMaxSize(), state = listState) {
                itemsIndexed(foodImageUrl) { index, image ->
                    AsyncImage(
                        model = image.imageUrl,
                        contentDescription = "Food Image ${index + 1}",
                        modifier = Modifier.fillParentMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one),
                        error = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(4.dp),
            ) {
                Text(
                    text = "${currentImageIndex + 1}/${foodImageUrl.size}", // Display current index
                    color = Color.Black,
                    fontSize = 12.sp,
                )
            }
        } else {
            AsyncImage(
                model = null,
                contentDescription = "Food Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one),
                error = painterResource(id = com.rhymezxcode.food.R.drawable.food_item_one),
            )
        }
    }
}

@Composable
fun DetailsSection(food: OneFoodItem?) {
    Column(
        modifier = Modifier
            .padding(16.dp),
    ) {
        Text(
            text = food?.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
        )

        Row(modifier = Modifier.padding(top = 8.dp)) {
            food?.foodTags?.forEach { tag ->
                Chip(text = tag ?: "")
            }
        }

        Text(
            text = food?.description?:"",
            modifier = Modifier.padding(top = 16.dp),
            fontSize = FONT_SIZE.sp,
            color = Color.Gray,
        )

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
            Text(text = "${food?.calories} Calories", fontSize = FONT_SIZE.sp)
        }

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
}


@Preview(showBackground = true)
@Composable
fun ShowFoodScreenPreview() {
//    ShowFoodScreen()
}
