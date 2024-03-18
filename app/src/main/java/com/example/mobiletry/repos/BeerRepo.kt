package com.example.mobiletry.repos

import android.util.Log
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
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()





    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerService = build.create(BeerService::class.java)
        getBeers()
    }

//Maybe make private later
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

    fun addBeer(beer: Beer) {
        beerService.addBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    val b: Beer? = response.body()
                    val beers: MutableList<Beer> = beersLiveData.value as MutableList<Beer>
                    beers.add(b!!)
                    beersLiveData.postValue(beers)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun deleteBeer(id: Int) {
        beerService.deleteBeer(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Deleted: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                    beersLiveData.value as MutableList<Beer>
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun updateBeer(beer: Beer) {
        beerService.updateBeer(beer.id, beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    val beers: MutableList<Beer> = beersLiveData.value as MutableList<Beer>
                    val index = beers.indexOfFirst { it.id == beer.id }
                    beers[index] = beer
                    beersLiveData.postValue(beers)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun sortByUser() {
        val beers: MutableList<Beer> = beersLiveData.value as MutableList<Beer>
        beers.sortBy { it.user }
        beersLiveData.postValue(beers)
    }

    //tjek hvilken funktion der er bedst i kotlin

    fun sortByBrewery() {
        beersLiveData.value?.sortedBy { it.brewery }
    }

    fun sortByBreweryDesc() {
        beersLiveData.value?.sortedByDescending { it.brewery }
    }

    fun sortByName() {
        beersLiveData.value?.sortedBy { it.name }
    }

    fun sortByNameDesc() {
        beersLiveData.value?.sortedByDescending { it.name }
    }


}