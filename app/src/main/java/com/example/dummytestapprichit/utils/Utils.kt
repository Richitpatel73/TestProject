package com.example.dummytestapprichit.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

class Utils {
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return run {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    fun displayToast(activity: Activity, toastMessage: String){
        Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show()
    }
}