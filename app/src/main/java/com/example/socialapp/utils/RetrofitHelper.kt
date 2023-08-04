package com.example.socialapp.utils

import android.util.Log
import com.example.socialapp.Interfacces.ApiInterFace
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    fun retrofitobj(): ApiInterFace {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        //+192168118

        val retro = Retrofit.Builder().baseUrl("http://192.168.1.18:9090/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return  retro.create(ApiInterFace::class.java)

    }

}