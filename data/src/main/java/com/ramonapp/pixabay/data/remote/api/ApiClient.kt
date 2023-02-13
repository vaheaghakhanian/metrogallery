package com.ramonapp.pixabay.data.remote.api

import com.google.gson.GsonBuilder
import com.ramonapp.pixabay.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        private const val API_BASE_URL = BuildConfig.API_URL
        private const val API_KEY = BuildConfig.API_KEY
        private const val CONNECTION_TIME_OUT = 5L
        private const val READ_TIME_OUT = 15L
        private const val WRITE_TIME_OUT = 15L

        private var gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(DefaultCallAdapterFactory())
                .client(getOkHttpClient())
                .build()
        }

        private fun getOkHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor {
                val originalRequest: Request = it.request()
                val builder = originalRequest.newBuilder()
                    .addHeader("Content-Type", "application/json")
                val url = originalRequest.url().newBuilder().addQueryParameter("key", API_KEY).build()
                val newRequest = builder.url(url).build()
                it.proceed(newRequest)
            }.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)

            return okHttpClientBuilder.build()
        }

    }
}