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

class ArtistsWithoutImageAdapter(var items: List<Artist>, val callback: Callback, val context: Context)
    : RecyclerView.Adapter<ArtistsWithoutImageAdapter.ArtistsWithoutImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ArtistsWithoutImageHolder(LayoutInflater
        .from(parent.context).inflate(R.layout.artists_without_image_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ArtistsWithoutImageHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ArtistsWithoutImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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