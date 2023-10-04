package com.exchangeratedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.exchangeratedemoapp.presentation.ui.screens.MainScreen
import com.exchangeratedemoapp.presentation.ui.theme.ExchangeRateDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRateDemoAppTheme {
                MainScreen()
            }
        }
    }
}