package com.example.mobiletry.repos

import retrofit2.Call
import retrofit2.http.*
import com.example.mobiletry.models.Beer

interface BeerService {
    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>
}