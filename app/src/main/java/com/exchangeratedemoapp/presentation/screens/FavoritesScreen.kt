package com.exchangeratedemoapp.presentation.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.presentation.components.CurrencyCard
import com.exchangeratedemoapp.presentation.theme.Header
import com.exchangeratedemoapp.presentation.theme.Outline
import com.exchangeratedemoapp.presentation.theme.TextDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {

    val items = listOf(
        Currency("EUR", "BYN", 3.345461, true),
        Currency("EUR", "AED", 3.345461, false),
        Currency("AED", "RUB", 3.345461, true),
        Currency("EUR", "AED", 3.345461, true),
        Currency("RUB", "AED", 6.345461, false)
    )
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
            items(items) { item ->
                CurrencyCard(currency = item)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FavoritesScreenPreview() {
    FavoritesScreen()
}