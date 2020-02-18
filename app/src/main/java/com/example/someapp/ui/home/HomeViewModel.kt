package com.example.someapp.ui.home

import Artist
import JsonTopAlbums
import JsonWeeklyArtists
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.someapp.models.ArtistType
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import kotlinx.coroutines.runBlocking


class HomeViewModel : ViewModel() {

    lateinit var moreArtists: List<Artist>

    val artists: MutableLiveData<List<Artist>> by lazy {
        MutableLiveData<List<Artist>>()
    }

    init {
        getArtists()
    }

    private fun getArtists(){
        runBlocking {
            Fuel.get("https://ws.audioscrobbler.com/2.0/?method=user.getWeeklyArtistChart&user=Arsikkk98&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json")
                .responseObject<JsonWeeklyArtists> { _, _, result ->
                    changeTypes(result.get().weeklyArtistChart.artists)
                    loadImages(result.get().weeklyArtistChart.artists.subList(0, 20))
                    val countOfArtists = result.get().weeklyArtistChart.artists.count()
                    moreArtists = result.get().weeklyArtistChart.artists.subList(20, countOfArtists)
                }
        }
    }

    private fun changeTypes(partOfArtists : List<Artist>){
        partOfArtists.forEach(){
            it.type = ArtistType.ARTIST_WITHOUT_IMAGE
        }
    }

    private fun loadImages(partOfArtists : List<Artist>){
        for (i in 0..9) {
            runBlocking {
                val url = "https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" +
                        partOfArtists[i].name + "&api_key=d0d3d3e2f51e2f5767d7bffeb1d733da&format=json&limit=1"
                Fuel.get(url)
                    .responseObject<JsonTopAlbums> { _, _, result ->
                        partOfArtists[i].image = result.get()?.topAlbums.albums[0].images[3].url
                        partOfArtists[i].type = ArtistType.ARTIST_WITH_IMAGE

                        if (i == 9) {
                            partOfArtists[0].type = ArtistType.FIRST_ARTIST
                            artists.value = partOfArtists
                        }
                    }
            }
        }
    }
}