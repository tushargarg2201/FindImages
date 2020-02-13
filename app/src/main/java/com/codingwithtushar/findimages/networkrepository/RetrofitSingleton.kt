package com.codingwithtushar.findimages.networkrepository

import com.codingwithtushar.findimages.ui.MainApplication
import com.codingwithtushar.findimages.utils.Constant
import com.codingwithtushar.findimages.utils.NetworkUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level


class RetrofitSingleton {
    companion object {

        private const val cacheSize = 5 * 1024 * 1024.toLong() // 5 MB
        private val cache = Cache(MainApplication.getContext().cacheDir, cacheSize)

        private val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addInterceptor(interceptor)
            .connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()



        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        private val retrofitInstance: ApiInterface = retrofit.create(ApiInterface::class.java)

        fun getRetrofitInstance(): ApiInterface {
            return retrofitInstance
        }
    }
}