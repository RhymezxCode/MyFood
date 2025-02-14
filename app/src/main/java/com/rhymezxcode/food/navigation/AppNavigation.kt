package com.rhymezxcode.food.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.ui.AddFoodScreen
import com.rhymezxcode.food.ui.HomeScreen

@Composable
fun AppNavigation(padding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home", // Set the initial screen
        modifier = Modifier.padding(padding),
    ) {
        composable("home") {
            HomeScreen()
        }
        composable("add") {
            AddFoodScreen()
        }
        // Add more composable destinations for your other screens
    }
}
