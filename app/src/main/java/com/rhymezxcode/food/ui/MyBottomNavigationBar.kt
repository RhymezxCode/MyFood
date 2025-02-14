package com.rhymezxcode.food.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.R
import com.rhymezxcode.food.navigation.NavigationItem

@Composable
fun MyBottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = getNavigationItems()

    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        BottomNavContent(items, currentRoute, navController)
    }
}

@Composable
fun BottomNavContent(
    items: List<NavigationItem>,
    currentRoute: String?,
    navController: NavController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            BottomNavItem(item, isSelected = currentRoute == item.route, navController)
        }
    }
}

@Composable
fun BottomNavItem(
    item: NavigationItem,
    isSelected: Boolean,
    navController: NavController,
) {
    IconButton(
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
    ) {
        val iconPainter = painterResource(id = item.icon)
        Icon(
            painter = iconPainter,
            contentDescription = item.title,
            tint = if (isSelected) Color.Blue else Color.Gray,
        )
    }

    if (isSelected) {
        Text(text = item.title, fontWeight = FontWeight.Bold)
    }
}

fun getNavigationItems(): List<NavigationItem> =
    listOf(
        NavigationItem("Home", "home", R.drawable.ic_home),
        NavigationItem("Generator", "generator", R.drawable.ic_generator),
        NavigationItem("Add", "add", R.drawable.ic_add),
        NavigationItem("Favourite", "favourite", R.drawable.ic_favorite),
        NavigationItem("Planner", "planner", R.drawable.ic_planner),
    )
