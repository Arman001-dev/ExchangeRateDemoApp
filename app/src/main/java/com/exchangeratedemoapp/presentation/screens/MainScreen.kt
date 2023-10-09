package com.exchangeratedemoapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.presentation.navigation.NavBar
import com.exchangeratedemoapp.presentation.navigation.NavBarItem
import com.exchangeratedemoapp.presentation.screens.currencies.CurrenciesScreen
import com.exchangeratedemoapp.presentation.screens.favorites.FavoritesScreen
import com.exchangeratedemoapp.presentation.screens.filters.FiltersScreen

sealed class NavDestinations(val route: String) {
    data object Currencies : NavDestinations("currencies")
    data object Favorites : NavDestinations("favorites")
}

sealed class FavoritesNavDestination(val route: String) {
    data object FavoritesScreen : FavoritesNavDestination("favorites-screen")
}

sealed class CurrenciesNavDestination(val route: String) {
    data object CurrenciesScreen : CurrenciesNavDestination("currencies-screen")
    data object FiltersScreen : CurrenciesNavDestination("filters-screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "${CurrenciesNavDestination.FiltersScreen.route}/{current_filter}" -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavBar(
                    items = listOf(
                        NavBarItem(
                            name = stringResource(R.string.currencies),
                            route = NavDestinations.Currencies.route,
                            icon = painterResource(id = R.drawable.ic_currencies),
                        ),
                        NavBarItem(
                            name = stringResource(R.string.favorites),
                            route = NavDestinations.Favorites.route,
                            icon = painterResource(id = R.drawable.ic_favorites_on),
                        ),
                    ),
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            NavHost(
                navController = navController,
                startDestination = NavDestinations.Currencies.route,
            ) {

                navigation(
                    startDestination = FavoritesNavDestination.FavoritesScreen.route,
                    route = NavDestinations.Favorites.route
                ) {
                    composable(FavoritesNavDestination.FavoritesScreen.route) {
                        FavoritesScreen()
                    }
                }

                navigation(
                    startDestination = "${CurrenciesNavDestination.CurrenciesScreen.route}/{filter}",
                    route = NavDestinations.Currencies.route
                ) {
                    composable(
                        route = "${CurrenciesNavDestination.CurrenciesScreen.route}/{filter}",
                        arguments = listOf(
                            navArgument("filter") {
                                type = NavType.StringType
                            })
                    ) { backStackEntry ->
                        val filter = backStackEntry.arguments?.getString("filter") ?: ""
                        CurrenciesScreen(
                            filter = filter,
                            onNavigateToFilterScreen = { currentFilter -> navController.navigate("${CurrenciesNavDestination.FiltersScreen.route}/${currentFilter}") }
                        )
                    }

                    composable(
                        route = "${CurrenciesNavDestination.FiltersScreen.route}/{current_filter}",
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
                                navController.navigate("${CurrenciesNavDestination.CurrenciesScreen.route}/${filter}") {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}