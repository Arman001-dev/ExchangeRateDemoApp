package com.exchangeratedemoapp.presentation.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.presentation.components.CurrencyCard
import com.exchangeratedemoapp.presentation.theme.Header
import com.exchangeratedemoapp.presentation.theme.Outline
import com.exchangeratedemoapp.presentation.theme.TextDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteCurrencies by favoritesViewModel.favoriteCurrencies.collectAsState()

    LaunchedEffect(true) {
        favoritesViewModel.getFavoriteCurrencies()
    }
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.favorites),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        color = TextDefault,
                    )
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Header
            )
        )
        Divider(thickness = 1.dp, color = Outline)
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoriteCurrencies ?: emptyList(), key = {
                it.key
            }) { currency ->
                CurrencyCard(
                    showBaseCurrency = true,
                    currency = currency,
                    insertFavoriteRate = { favoritesViewModel.insertFavoriteCurrency(currency) },
                    deleteFavoriteRate = { favoritesViewModel.deleteFavoriteCurrency(currency) }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FavoritesScreenPreview() {
    FavoritesScreen()
}