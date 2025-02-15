package com.rhymezxcode.food.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.R
import com.rhymezxcode.food.navigation.NavigationItem

@Composable
fun MyBottomNavigationBar(navController: NavController) {
    val items = getNavigationItems()

    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        BottomNavContent(items, navController)
    }
}


@Composable
fun BottomNavContent(
    items: List<NavigationItem>,
    navController: NavController, // removed currentRoute
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            BottomNavItem(item, navController)
        }
    }
}

@Composable
fun BottomNavItem(
    item: NavigationItem,
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val isSelected = currentRoute == item.route

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                if (navController.currentDestination?.route != item.route) {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                tint = if (isSelected) Color.Blue else Color.Gray,
            )
        }

        Text(
            text = item.title,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.Blue else Color.Gray,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
        )
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

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    MyBottomNavigationBar(navController = navController)
}
