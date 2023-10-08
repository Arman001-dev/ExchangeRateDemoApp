package com.exchangeratedemoapp.presentation.screens

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
import com.exchangeratedemoapp.presentation.screens.favorites.FavoritesScreen

sealed class BottomBarDestination(val route: String) {
    data object CurrenciesMainScreen : BottomBarDestination("currencies-main-screen")
    data object FavoritesScreen : BottomBarDestination("favorites-screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavBar(
                items = listOf(
                    NavBarItem(
                        name = stringResource(R.string.currencies),
                        route = BottomBarDestination.CurrenciesMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_currencies),
                    ),
                    NavBarItem(
                        name = stringResource(R.string.favorites),
                        route = BottomBarDestination.FavoritesScreen.route,
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomBarDestination.CurrenciesMainScreen.route,
            ) {

                composable(BottomBarDestination.CurrenciesMainScreen.route) {
                    CurrencyMainScreen()
                }

                composable(BottomBarDestination.FavoritesScreen.route) {
                    FavoritesScreen()
                }
            }
        }
    }
}