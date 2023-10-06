package com.exchangeratedemoapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.presentation.theme.Default
import com.exchangeratedemoapp.presentation.theme.Primary
import com.exchangeratedemoapp.presentation.theme.Secondary

@Composable
fun ExpandableItems(
    currentCurrency: CurrenciesEnum = CurrenciesEnum.EUR,
    onCurrencyClick: (currency: CurrenciesEnum) -> Unit = {}
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val arrowDirection by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 360f, label = ""
    )

    Card(
        modifier = Modifier
            .width(310.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Secondary),
        colors = CardDefaults.cardColors(containerColor = Default),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = currentCurrency.label,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium))
                    ),
                )
                Icon(
                    modifier = Modifier
                        .rotate(arrowDirection)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    tint = Primary,
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    expandFrom = Alignment.CenterVertically,
                )
            ) {
                Column {
                    CurrenciesEnum.values().forEach { currency ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(color = if (currency.label == currentCurrency.label) Secondary else Default)
                            .clickable {
                                onCurrencyClick(currency)
                                isExpanded = !isExpanded
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                text = currency.label,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ExpandableItemsPreview() {
    ExpandableItems()
}