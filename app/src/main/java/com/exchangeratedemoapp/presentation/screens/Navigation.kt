package com.exchangeratedemoapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exchangeratedemoapp.presentation.screens.filters.FiltersScreen

sealed class NavDestination(val route: String) {
    data object MainScreen : NavDestination("main-screen")
    data object FiltersScreen : NavDestination("filters-screen")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestination.MainScreen.route) {

        composable(NavDestination.MainScreen.route) {
            MainScreen(onNavigateToFilterScreen = { navController.navigate(NavDestination.FiltersScreen.route) })
        }

        composable(NavDestination.FiltersScreen.route) {
            FiltersScreen(
                onBackClick = { navController.navigateUp() },
                onNavigateToMainScreen = { navController.navigate(NavDestination.MainScreen.route) }
            )
        }
    }
}