package com.example.someapp.ui.home

import Artist
import JsonTopAlbums
import JsonWeeklyArtists
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import kotlinx.coroutines.runBlocking


class HomeViewModel : ViewModel() {

    val artistsWithImages: MutableLiveData<List<Artist>> by lazy {
        MutableLiveData<List<Artist>>()
    }
    val artistsWithoutImages: MutableLiveData<List<Artist>> by lazy {
        MutableLiveData<List<Artist>>()
    }

    init {
        getSomething()
    }

    private fun getSomething(){
        runBlocking {
            Fuel.get("http://ws.audioscrobbler.com/2.0/?method=user.getWeeklyArtistChart&user=Arsikkk98&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json")
                .responseObject<JsonWeeklyArtists> { _, _, result ->
                    loadImages(result.get().weeklyArtistChart.artists.subList(0, 10))
                    val countOfArtists = result.get().weeklyArtistChart.artists.count()
                    artistsWithoutImages.value = result.get().weeklyArtistChart.artists.subList(10, countOfArtists)
                }
        }
    }

    private fun loadImages(artists : List<Artist>){
        artists.forEach {
            runBlocking {
                val url = "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" +
                        it.name + "&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json&limit=1"
                Fuel.get(url)
                    .responseObject<JsonTopAlbums> { _, _, result ->
                        it.image = result.get()?.topAlbums.albums[0].images[3].url

                        if (it == artists?.last())
                            artistsWithImages.value = artists
                    }
            }
        }
    }
}