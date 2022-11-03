package com.example.jetpackcompose.services

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroInstance {
    companion object{

        var baseURl ="https://api.themoviedb.org/3/discover/movie/"
        fun getRetroInstance(): Retrofit {
//            val logging =HttpLoggingInterceptor()
//            logging.level=(HttpLoggingInterceptor.Level.BODY)
            val client= OkHttpClient.Builder()
            //client.addInterceptor(logging)
            client.connectTimeout(30000, TimeUnit.SECONDS)
            client.readTimeout(30000, TimeUnit.SECONDS)
            client.connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            return Retrofit.Builder().baseUrl(baseURl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create()).build()
            //.addConverterFactory(MoshiConverterFactory.create()).build()
        }
    }

}