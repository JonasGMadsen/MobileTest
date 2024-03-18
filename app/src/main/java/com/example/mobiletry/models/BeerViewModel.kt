package com.example.mobiletry.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobiletry.repos.BeerRepo

class BeerViewModel : ViewModel() {
    private val repository = BeerRepo()
    val beersLiveData: LiveData<List<Beer>> = repository.beersLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData
    val reloadingLiveData: LiveData<Boolean> = repository.reloadingLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getBeers()
    }

    operator fun get(index: Int): Beer? {
        return beersLiveData.value?.get(index)
    }

    fun add(beer: Beer) {
        repository.addBeer(beer)
    }

    fun delete(id: Int) {
        repository.deleteBeer(id)
    }

    fun update(beer: Beer) {
        repository.updateBeer(beer)
    }

    fun sortByUser() {
        repository.sortByUser()
    }

    fun sortByBrewery() {
        repository.sortByBrewery()
    }

    fun sortByBreweryDescending() {
        repository.sortByBreweryDesc()
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByNameDescending() {
        repository.sortByNameDesc()
    }
}