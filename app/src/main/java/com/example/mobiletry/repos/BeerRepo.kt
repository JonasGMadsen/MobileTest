package com.example.mobiletry.repos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeerRepo {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"







    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}