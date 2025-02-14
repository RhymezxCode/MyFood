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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.R
import com.rhymezxcode.food.navigation.NavigationItem

@Composable
fun MyBottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        NavigationItem(
            title = "Home",
            route = "home",
            icon = R.drawable.ic_home
        ),
        NavigationItem(
            title = "Generator",
            route = "generator",
            icon = R.drawable.ic_generator
        ),
        NavigationItem(
            title = "Add",
            route = "add",
            icon = R.drawable.ic_add
        ),
        NavigationItem(
            title = "Favourite",
            route = "favourite",
            icon = R.drawable.ic_favorite
        ),
        NavigationItem(
            title = "Planner",
            route = "planner",
            icon = R.drawable.ic_planner
        ),
    )

    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                IconButton(
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    val iconPainter = painterResource(id = item.icon)
                    Icon(
                        painter = iconPainter,
                        contentDescription = item.title,
                        tint = if (isSelected) Color.Blue else Color.Gray
                    )
                }

                if (isSelected) {
                    Text(text = item.title, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
