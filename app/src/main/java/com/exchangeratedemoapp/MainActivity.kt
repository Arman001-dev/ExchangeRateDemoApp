package com.exchangeratedemoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import com.exchangeratedemoapp.ExchangeRatesApplication.Companion.networkStateFlow
import com.exchangeratedemoapp.presentation.screens.Navigation
import com.exchangeratedemoapp.presentation.theme.ExchangeRateDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRateDemoAppTheme {
                val networkState = networkStateFlow.collectAsState()
                when (networkState.value) {
                    true -> Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
                    false -> Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
                }
                Navigation()
            }
        }
    }
}