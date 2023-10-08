package com.exchangeratedemoapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.exchangeratedemoapp.presentation.screens.currencies.CurrenciesScreen
import com.exchangeratedemoapp.presentation.screens.filters.FiltersScreen

sealed class NavDestination(val route: String) {
    data object CurrenciesScreen : NavDestination("currencies-screen")
    data object FiltersScreen : NavDestination("filters-screen")
}

@Composable
fun CurrencyMainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "${NavDestination.CurrenciesScreen.route}/{filter}"
    ) {

        composable(
            route = "${NavDestination.CurrenciesScreen.route}/{filter}",
            arguments = listOf(
                navArgument("filter") {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            val filter = backStackEntry.arguments?.getString("filter") ?: ""
            CurrenciesScreen(
                filter = filter,
                onNavigateToFilterScreen = { currentFilter -> navController.navigate("${NavDestination.FiltersScreen.route}/${currentFilter}") }
            )
        }

        composable(
            route = "${NavDestination.FiltersScreen.route}/{current_filter}",
            arguments = listOf(
                navArgument("current_filter") {
                    type = NavType.StringType
                    nullable = true
                })
        ) { backStackEntry ->
            val currentFilter = backStackEntry.arguments?.getString("current_filter") ?: ""
            FiltersScreen(
                currentFilter = currentFilter,
                onBackClick = { navController.navigateUp() },
                onNavigateToCurrenciesScreen = { filter ->
                    navController.navigate("${NavDestination.CurrenciesScreen.route}/${filter}") {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}