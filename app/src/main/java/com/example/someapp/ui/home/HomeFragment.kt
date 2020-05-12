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
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = root.findViewById(R.id.homeProgress)
        recycler = root.findViewById(R.id.artistsRecycler)

        homeViewModel.artists.observe(viewLifecycleOwner, Observer {
            artists = it
            updateArtistsRecycler()

            progressBar.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        })

        return root
    }

    private fun updateArtistsRecycler(){
        val artistsAdapter = ArtistsAdapter(artists,
            object : ArtistsAdapter.Callback {
                override fun onItemClicked(item: Artist) {
                    //TODO Сюда придёт элемент, по которому кликнули. Можно дальше с ним работать
            }
        }, requireContext())

        recycler.adapter = artistsAdapter
    }
}