package com.example.chatapp.viewModels.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest


class ConnectivityState(private val context: Context) {
    private val connectivityManager: ConnectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val timeOut: Int = 1000

    private val request: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()


    fun setUpListener(netConnectivity: NetConnectivity) {
        connectivityManager.requestNetwork(request, object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                netConnectivity.network(State.AVAILABLE)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                netConnectivity.network(State.UNAVAILABLE)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                netConnectivity.network(State.UNAVAILABLE)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                netConnectivity.network(State.UNAVAILABLE)
            }

        }, timeOut)
    }
}