package com.example.someapp.ui.home

import JsonWeeklyArtists
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import kotlinx.coroutines.runBlocking


class HomeViewModel : ViewModel() {

    val artist: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        getSomething()
    }

    private fun getSomething(){
        artist.value = "EMM"
        runBlocking {
            Fuel.get("http://ws.audioscrobbler.com/2.0/?method=user.getWeeklyArtistChart&user=Arsikkk98&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json")
                .responseObject<JsonWeeklyArtists> { _, _, result ->
                    artist.value = result.get().weeklyArtistChart.artist[0].name
            }
        }
    }
}