package com.exchangeratedemoapp.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.exchangeratedemoapp.R
import com.exchangeratedemoapp.presentation.theme.TextDefault

@Composable
fun NoInternetDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = stringResource(id = R.string.do_not_show))
            }
        },
        text = {
            Text(
                text = stringResource(id = R.string.no_internet),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    color = TextDefault,
                )
            )
        }
    )
}