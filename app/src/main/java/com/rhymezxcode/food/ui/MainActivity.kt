package com.rhymezxcode.food.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
    Scaffold(
        bottomBar = { MyBottomNavigationBar() },
    ) { padding ->
        AppNavigation(padding = padding) //  Manage navigation in a separate composable
    }
}

@Preview(showBackground = true)
@Composable
fun MyDefaultPreview() {
    MyFoodTheme {
        MainScreen()
    }
}
