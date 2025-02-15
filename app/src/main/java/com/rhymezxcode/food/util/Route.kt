package com.rhymezxcode.food.util

sealed class Route(val path: String) {
    object Home : Route("home")
    object Generator : Route("generator")
    object ShowFood : Route("show_food/{foodId}")
    object Add : Route("add")
    object Favourite : Route("favourite")
    object Planner : Route("planner")

    // Helper function to create a route for ShowFood with a specific foodId
    fun showFoodPath(foodId: Int?): String {
        return path.replace("{foodId}", foodId.toString())
    }
}
