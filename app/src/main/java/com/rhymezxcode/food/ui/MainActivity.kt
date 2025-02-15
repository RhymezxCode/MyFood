package com.rhymezxcode.food.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.navigation.AppNavigation
import com.rhymezxcode.food.theme.MyFoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController() // Create NavController once
    Scaffold(
        bottomBar = { MyBottomNavigationBar(navController) },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) { // Apply padding properly
            AppNavigation(navController)
        }
    }
}

@Composable
fun GeneratorScreen() {
    Text("Generator Screen")
}

@Composable
fun FavouriteScreen() {
    Text("Favourite Screen")
}

@Composable
fun PlannerScreen() {
    Text("Planner Screen")
}


@Preview(showBackground = true)
@Composable
fun MyDefaultPreview() {
    MyFoodTheme {
        MainScreen()
    }
}
