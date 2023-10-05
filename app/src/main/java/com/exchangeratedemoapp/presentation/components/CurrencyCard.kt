package com.exchangeratedemoapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.presentation.theme.Secondary
import com.exchangeratedemoapp.presentation.theme.TextDefault
import com.exchangeratedemoapp.presentation.theme.Yellow

@Composable
fun CurrencyCard(currency: Currency) {
    Card(colors = CardDefaults.cardColors(containerColor = com.exchangeratedemoapp.presentation.theme.Card)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = currency.currency,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    color = TextDefault,
                )
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = currency.rate.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    color = TextDefault,
                )
            )
            Icon(
                painter = if (currency.isFavorite) painterResource(id = R.drawable.ic_favorites_on) else painterResource(id = R.drawable.ic_favorites_off),
                contentDescription = "Currency favorite",
                tint = if (currency.isFavorite) Yellow else Secondary,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CurrencyCardPreview() {
    CurrencyCard(Currency("EUR", "AED", 3.345461, true))
}