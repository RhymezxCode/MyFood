package com.rhymezxcode.food.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rhymezxcode.food.ui.AddFoodScreen
import com.rhymezxcode.food.ui.FavouriteScreen
import com.rhymezxcode.food.ui.GeneratorScreen
import com.rhymezxcode.food.ui.HomeScreen
import com.rhymezxcode.food.ui.PlannerScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") { HomeScreen() }
        composable("generator") { GeneratorScreen() }
        composable("add") { AddFoodScreen() }
        composable("favourite") { FavouriteScreen() }
        composable("planner") { PlannerScreen() }
    }
}

