package com.rhymezxcode.food.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rhymezxcode.food.navigation.AppNavigation
import com.rhymezxcode.food.theme.MyFoodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb())
        )

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
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            MyBottomNavigationBar(navController)
        },
        modifier = Modifier
            .fillMaxSize()
            .imePadding() // Fix for system insets & keyboard overlap
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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
