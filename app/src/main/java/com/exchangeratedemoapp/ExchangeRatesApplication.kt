package com.exchangeratedemoapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@HiltAndroidApp
class ExchangeRatesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        listenToNetworkChange()
    }

    private fun listenToNetworkChange() {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkStateFlow.update { true }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkStateFlow.update { false }
            }
        }
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    companion object {
        val networkStateFlow = MutableStateFlow(false)
    }
}