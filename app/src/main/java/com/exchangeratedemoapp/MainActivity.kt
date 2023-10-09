package com.exchangeratedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.exchangeratedemoapp.presentation.screens.Navigation
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