package com.exchangeratedemoapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.BorderStroke
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
import com.exchangeratedemoapp.presentation.ui.theme.Default
import com.exchangeratedemoapp.presentation.ui.theme.Primary
import com.exchangeratedemoapp.presentation.ui.theme.Secondary

@Composable
fun ExpandableItems() {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val list = listOf("AED", "RUB", "USD")

    val arrowDirection by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 360f, label = ""
    )

    Card(
        modifier = Modifier
            .width(310.dp)
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Secondary),
        colors = CardDefaults.cardColors(containerColor = Default),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "EUR",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium))
                    )
                )
                Icon(
                    modifier = Modifier.rotate(arrowDirection),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    tint = Primary
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    expandFrom = Alignment.CenterVertically,
                )
            ) {
                Column {
                    list.forEach { currency ->
                        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text(text = currency)
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