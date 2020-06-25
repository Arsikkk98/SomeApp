package com.example.someapp.ui.home

import Artist
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.someapp.R
import com.example.someapp.models.ArtistType
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class ArtistsAdapter(var items: List<Artist>, val callback: Callback, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {
            ArtistType.FIRST_ARTIST.ordinal -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.first_artist_item, parent, false)
                return ArtistWithImageViewHolder(view)
            }
            ArtistType.ARTIST_WITH_IMAGE.ordinal -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.artists_with_image_item, parent, false)
                return ArtistWithImageViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.artists_without_image_item, parent, false)
                return ArtistWithoutImageViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position].type){
            ArtistType.FIRST_ARTIST -> (holder as ArtistWithImageViewHolder).bind(items[position])
            ArtistType.ARTIST_WITH_IMAGE -> (holder as ArtistWithImageViewHolder).bind(items[position])
            else -> (holder as ArtistWithoutImageViewHolder).bind(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    inner class ArtistWithImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val count = itemView.findViewById<TextView>(R.id.count)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: Artist) {
            name.text = item.attr.rank.toString()+ ". " + item.name
            count.text = item.playCount.toString() + " times"

            if(item.image != null && item.image.isNotEmpty()){
                val builder = Picasso.Builder(context)
                builder.downloader(OkHttp3Downloader(context))
                builder.build().load(item.image).placeholder(R.drawable.placeholder_artist)
                    .error(R.drawable.placeholder_artist_error).into(image)
            }

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    inner class ArtistWithoutImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val count = itemView.findViewById<TextView>(R.id.count)

        fun bind(item: Artist) {
            name.text = item.attr.rank.toString()+ ". " + item.name
            count.text = item.playCount.toString() + " times"

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Artist)
    }
}