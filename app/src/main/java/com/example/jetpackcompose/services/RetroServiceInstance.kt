package com.example.jetpackcompose.services

import com.example.jetpackcompose.model.MoviesModel
import retrofit2.Call
import retrofit2.http.*

interface RetroServiceInstance {

    @GET(".")
    @Headers("Accept:application/json","content-Type:application/json")
    fun getMovieResults(@Query("api_key") apiKey:String): Call<MoviesModel>

}