package com.exchangeratedemoapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.presentation.navigation.NavBar
import com.exchangeratedemoapp.presentation.navigation.NavBarItem

sealed class BottomBarDestination(val route: String) {
    data object CurrenciesScreen : BottomBarDestination("currencies-screen")
    data object FavoritesScreen : BottomBarDestination("favorites-screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavBar(
                items = listOf(
                    NavBarItem(
                        name = stringResource(R.string.currencies),
                        route = BottomBarDestination.CurrenciesScreen.route,
                        icon = painterResource(id = R.drawable.icon_currencies),
                    ),
                    NavBarItem(
                        name = stringResource(R.string.favorites),
                        route = BottomBarDestination.FavoritesScreen.route,
                        icon = painterResource(id = R.drawable.icon_favorites_on),
                    ),
                ),
                navController = bottomBarNavController,
                onItemClick = {
                    bottomBarNavController.navigate(it.route) {
                        popUpTo(bottomBarNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            NavHost(navController = bottomBarNavController, startDestination = BottomBarDestination.CurrenciesScreen.route) {

                composable(BottomBarDestination.CurrenciesScreen.route) {
                    CurrenciesScreen()
                }

                composable(BottomBarDestination.FavoritesScreen.route) {
                    FavoritesScreen()
                }
            }
        }
    }
}