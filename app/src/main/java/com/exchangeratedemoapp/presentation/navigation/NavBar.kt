package com.exchangeratedemoapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.presentation.theme.LightPrimary
import com.exchangeratedemoapp.presentation.theme.Primary
import com.exchangeratedemoapp.presentation.theme.Secondary
import com.exchangeratedemoapp.presentation.theme.TextDefault

@Composable
fun NavBar(
    items: List<NavBarItem> = emptyList(),
    navController: NavController,
    onItemClick: (NavBarItem) -> Unit = {},
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(tonalElevation = 0.dp) {
        items.forEach { navBarItem ->
            val selected = currentBackStackEntry?.destination?.route == navBarItem.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(navBarItem) },
                icon = {
                    Icon(
                        painter = navBarItem.icon,
                        contentDescription = navBarItem.name,
                    )
                },
                label = {
                    Text(
                        text = navBarItem.name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = LightPrimary,
                    selectedIconColor = Primary,
                    unselectedIconColor = Secondary,
                    selectedTextColor = TextDefault,
                    unselectedTextColor = Secondary
                ),
            )
        }
    }
}