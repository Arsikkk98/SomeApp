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
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class ArtistsWithImageAdapter(var items: List<Artist>, val callback: Callback, val context: Context) : RecyclerView.Adapter<ArtistsWithImageAdapter.ArtistsWithImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ArtistsWithImageHolder(LayoutInflater
        .from(parent.context).inflate(R.layout.artists_with_image_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ArtistsWithImageHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ArtistsWithImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val count = itemView.findViewById<TextView>(R.id.count)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: Artist) {
            name.text = item.attr.rank.toString()+ ". " + item.name
            count.text = item.playCount.toString() + " times"

            val builder = Picasso.Builder(context)
            builder.downloader(OkHttp3Downloader(context))
            builder.build().load(item.image).placeholder(R.drawable.placeholder_artist)
                .error(R.drawable.placeholder_artist_error).into(image)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Artist)
    }
}