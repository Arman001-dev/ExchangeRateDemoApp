package com.exchangeratedemoapp.presentation.screens.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.domain.models.FiltersOptionEnum
import com.exchangeratedemoapp.presentation.theme.Header
import com.exchangeratedemoapp.presentation.theme.OnPrimary
import com.exchangeratedemoapp.presentation.theme.Outline
import com.exchangeratedemoapp.presentation.theme.Primary
import com.exchangeratedemoapp.presentation.theme.Secondary
import com.exchangeratedemoapp.presentation.theme.TextDefault
import com.exchangeratedemoapp.presentation.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    currentFilter: String= "",
    onBackClick: () -> Unit = {},
    onNavigateToCurrenciesScreen: (String) -> Unit = {}
) {
    var selectedFilter by rememberSaveable { mutableStateOf(currentFilter) }

    Column {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.filters),
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = stringResource(id = R.string.sort_by),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    color = TextSecondary,
                ),
            )
            FiltersOptionEnum.values().forEach { filter ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = TextDefault,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                        ),
                        text = filter.label,
                    )
                    RadioButton(
                        selected = (selectedFilter == filter.label),
                        onClick = { selectedFilter = filter.label },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Secondary,
                        )
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToCurrenciesScreen(selectedFilter) },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.apply),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            color = OnPrimary,
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FiltersScreenPreview() {
    FiltersScreen()
}