package com.exchangeratedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.exchangeratedemoapp.presentation.screens.MainScreen
import com.exchangeratedemoapp.presentation.components.NoInternetDialog
import com.exchangeratedemoapp.presentation.theme.ExchangeRateDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRateDemoAppTheme {
                var showNoNetworkDialog by rememberSaveable { mutableStateOf(false) }
                val networkState = ExchangeRatesApplication.networkStateFlow.collectAsState()
                LaunchedEffect(key1 = networkState.value) {
                    showNoNetworkDialog = !networkState.value
                }

                if (showNoNetworkDialog) {
                    NoInternetDialog(
                        onDismissRequest = { showNoNetworkDialog = false },
                        onConfirmButtonClick = { showNoNetworkDialog = false }
                    )
                }
                MainScreen()
            }
        }
    }
}