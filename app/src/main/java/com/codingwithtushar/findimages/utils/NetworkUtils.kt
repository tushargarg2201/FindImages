package com.codingwithtushar.findimages.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {
    companion object{
        fun isNetworkConnected(context:Context):Boolean{
            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

    }
}