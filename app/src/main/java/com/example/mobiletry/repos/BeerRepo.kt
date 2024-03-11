package com.example.mobiletry.repos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import com.example.mobiletry.models.Beer

class BeerRepo {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"


    private val beerService: BeerService
    val beersLiveData: MutableLiveData<List<Beer>> = MutableLiveData<List<Beer>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val reloadingLiveData: MutableLiveData<Boolean> = MutableLiveData()





    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerService = build.create(BeerService::class.java)
        getBeers()
    }

    fun getBeers() {
        reloadingLiveData.value = true
        beerService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                reloadingLiveData.value = false
                if (response.isSuccessful) {
                    val b: List<Beer>? = response.body()
                    beersLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                reloadingLiveData.value = false
                errorMessageLiveData.postValue(t.message)
            }
        })
    }




}