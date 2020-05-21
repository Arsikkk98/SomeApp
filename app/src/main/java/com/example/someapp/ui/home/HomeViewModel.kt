package com.example.someapp.ui.home

import Artist
import JsonTopAlbums
import JsonWeeklyArtists
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.someapp.models.ArtistType
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.gson.responseObject
import kotlinx.coroutines.runBlocking


class HomeViewModel : ViewModel() {

    lateinit var moreArtists: List<Artist>

    val artists: MutableLiveData<List<Artist>> by lazy {
        MutableLiveData<List<Artist>>()
    }

    val error: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        getArtists()
    }

    private fun getArtists(){
        runBlocking {
            Fuel.get("https://ws.audioscrobbler.com/2.0/?method=user.getWeeklyArtistChart&user=tatraef&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json")
                .responseObject<JsonWeeklyArtists> { _, _, result ->
                    when (result) {
                        is Result.Success -> {
                            changeTypesAndImages(result.get().weeklyArtistChart.artists.subList(0, 20))
                            val countOfArtists = result.get().weeklyArtistChart.artists.count()
                            moreArtists = result.get().weeklyArtistChart.artists.subList(20, countOfArtists)
                        }
                        is Result.Failure -> {
                            error.value = 1
                        }
                    }
                }
        }
    }

    private fun changeTypesAndImages(partOfArtists : List<Artist>){
        partOfArtists[0].type = ArtistType.FIRST_ARTIST

        for (i in 1..9) {
            partOfArtists[i].type = ArtistType.ARTIST_WITH_IMAGE
        }

        for (i in 10..19) {
            partOfArtists[i].type = ArtistType.ARTIST_WITHOUT_IMAGE
        }

        artists.value = partOfArtists
        loadImages(partOfArtists)
    }

    private fun loadImages(partOfArtists : List<Artist>){
        for (i in 0..9) {
            runBlocking {
                val url = "https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" +
                        partOfArtists[i].name +
                        "&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json&limit=1"

                Fuel.get(url).responseObject<JsonTopAlbums> { _, _, result ->
                    when (result) {
                        is Result.Success -> {
                            partOfArtists[i].image = result.get().topAlbums.albums[0].images[3].url
                            if (i == 9) artists.value = partOfArtists
                        }
                        is Result.Failure -> {
                            error.value = 2
                        }
                    }
                }
            }
        }
    }
}