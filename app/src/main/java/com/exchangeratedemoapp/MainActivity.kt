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
                Navigation()
            }
        }
    }
}