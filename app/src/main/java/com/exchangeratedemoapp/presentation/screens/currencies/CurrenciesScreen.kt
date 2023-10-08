package com.exchangeratedemoapp.presentation.screens.currencies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.presentation.components.CurrencyCard
import com.exchangeratedemoapp.presentation.components.ExpandableItems
import com.exchangeratedemoapp.presentation.theme.Default
import com.exchangeratedemoapp.presentation.theme.Header
import com.exchangeratedemoapp.presentation.theme.Outline
import com.exchangeratedemoapp.presentation.theme.Primary
import com.exchangeratedemoapp.presentation.theme.Secondary
import com.exchangeratedemoapp.presentation.theme.TextDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(
    currenciesViewModel: CurrenciesViewModel = hiltViewModel(),
    filter: String = "",
    onNavigateToFilterScreen: (String?) -> Unit = {}
) {
    val currentCurrency by currenciesViewModel.currentCurrency.collectAsState()
    val exchangeRates by currenciesViewModel.exchangeRates.collectAsState()
    val ratesFilter by currenciesViewModel.ratesFilter.collectAsState()

    LaunchedEffect(true) {
        currenciesViewModel.setRatesFilter(filter)
        currenciesViewModel.getExchangeRates(currentCurrency ?: CurrenciesEnum.EUR)
    }
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.currencies),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        color = TextDefault,
                    )
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Header,
                navigationIconContentColor = Primary
            )
        )
        Row(
            modifier = Modifier
                .background(Header)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            currentCurrency?.let {
                ExpandableItems(it) {
                    currenciesViewModel.setCurrency(it)
                    currenciesViewModel.getExchangeRates(it)
                }
            }
            Card(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { onNavigateToFilterScreen(ratesFilter?.label) },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 1.dp, color = Secondary),
                colors = CardDefaults.cardColors(containerColor = Default),
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_filtr),
                    contentDescription = null,
                    tint = Primary
                )
            }
        }
        Divider(thickness = 1.dp, color = Outline)

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exchangeRates ?: emptyList()) { currency ->
                CurrencyCard(
                    currency = currency,
                    insertFavoriteRate = { currenciesViewModel.setFavoriteRate(currency) },
                    deleteFavoriteRate = { currenciesViewModel.deleteFavoriteRate(currency) }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CurrenciesScreenPreview() {
    CurrenciesScreen()
}