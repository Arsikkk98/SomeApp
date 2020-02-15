package com.example.someapp.ui.home

import Artist
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.someapp.R
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

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


        homeViewModel.artistsWithImages.observe(this, Observer {
            artists = it
            createFirstPlace()
            updateArtistsWithImageRecycler()

            val progressBar = root.findViewById<ProgressBar>(R.id.homeProgress)
            val constraintLayout = root.findViewById<ConstraintLayout>(R.id.homeMainLayout)
            progressBar.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
        })

        homeViewModel.artistsWithoutImages.observe(this, Observer {
            val recycler = root.findViewById<RecyclerView>(R.id.artistsWithoutImagesRecycler)

            val artistsWithoutImageAdapter = ArtistsWithoutImageAdapter(it,
                object : ArtistsWithoutImageAdapter.Callback {
                    override fun onItemClicked(item: Artist) {
                        //TODO Сюда придёт элемент, по которому кликнули. Можно дальше с ним работать
                    }
                }, requireContext())

            recycler.adapter = artistsWithoutImageAdapter
        })

        return root
    }

    private fun createFirstPlace(){
        var nameTextView = root.findViewById<TextView>(R.id.firstPlaceName)
        val countTextView = root.findViewById<TextView>(R.id.firstPlaceCount)
        val imageView = root.findViewById<ImageView>(R.id.firstPlaceImage)

        nameTextView.text = artists[0].attr.rank.toString() + ". " + artists[0].name
        countTextView.text = artists[0].playCount.toString() + " times"
        val builder = Picasso.Builder(requireContext())
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(artists[0].image).placeholder(R.drawable.placeholder_artist)
            .error(R.drawable.placeholder_artist_error).into(imageView)

    }

    private fun updateArtistsWithImageRecycler(){
        val recycler = root.findViewById<RecyclerView>(R.id.artistsWithImagesRecycler)

        val artistsWithImageAdapter = ArtistsWithImageAdapter(artists.subList(1, 10),
            object : ArtistsWithImageAdapter.Callback {
                override fun onItemClicked(item: Artist) {
                    //TODO Сюда придёт элемент, по которому кликнули. Можно дальше с ним работать
            }
        }, requireContext())

        recycler.adapter = artistsWithImageAdapter
    }

    private fun getPhotoFromLastfm(){
        var nameTextView = root.findViewById<TextView>(R.id.firstPlaceName)
        val countTextView = root.findViewById<TextView>(R.id.firstPlaceCount)
        val imageView = root.findViewById<TextView>(R.id.firstPlaceImage)

        nameTextView.text = artists[0].attr.rank.toString() + ". " + artists[0].name
        countTextView.text = artists[0].playCount.toString() + " times"

    }
}