package com.example.someapp.ui.home

import Artist
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.someapp.R


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var root : View
    private lateinit var artists : List<Artist>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)


        homeViewModel.artists.observe(this, Observer {
            artists = it
            updateArtistsRecycler()

            val progressBar = root.findViewById<ProgressBar>(R.id.homeProgress)
            val recycler = root.findViewById<RecyclerView>(R.id.artistsRecycler)

            progressBar.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        })

        return root
    }

    private fun updateArtistsRecycler(){
        val recycler = root.findViewById<RecyclerView>(R.id.artistsRecycler)

        val artistsAdapter = ArtistsAdapter(artists,
            object : ArtistsAdapter.Callback {
                override fun onItemClicked(item: Artist) {
                    //TODO Сюда придёт элемент, по которому кликнули. Можно дальше с ним работать
            }
        }, requireContext())

        recycler.adapter = artistsAdapter
    }
}