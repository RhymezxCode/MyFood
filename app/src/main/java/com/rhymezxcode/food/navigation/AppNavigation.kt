package com.rhymezxcode.food.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rhymezxcode.food.ui.AddFoodScreen
import com.rhymezxcode.food.ui.FavouriteScreen
import com.rhymezxcode.food.ui.GeneratorScreen
import com.rhymezxcode.food.ui.HomeScreen
import com.rhymezxcode.food.ui.PlannerScreen
import com.rhymezxcode.food.ui.ShowFoodScreen
import com.rhymezxcode.food.util.Route

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.path,
    ) {
        composable(Route.Home.path) { HomeScreen(navController = navController) }
        composable(Route.Generator.path) { GeneratorScreen() }
        composable(
            route = Route.ShowFood.path,
            arguments = listOf(
                navArgument("foodId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId")
            foodId?.let {
                ShowFoodScreen(foodId = it, navController = navController)
            }
        }
        composable(Route.Add.path) { AddFoodScreen(navController = navController) }
        composable(Route.Favourite.path) { FavouriteScreen() }
        composable(Route.Planner.path) { PlannerScreen() }
    }
}


