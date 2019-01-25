package com.example.mygooglephoto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivities
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

class AlbumSingleAdapter(val context: Context, val items : List<String>,
                         val covers : ArrayList<String>, val to : Activity)
    : RecyclerView.Adapter<AlbumSingleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumSingleAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_single_album,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: AlbumSingleAdapter.ViewHolder,
        position: Int
    ) {
        holder.view.findViewById<TextView>(R.id.album_name_single).text = items[position]

        holder.view.findViewById<FrameLayout>(R.id.label_album_single)
            .setOnClickListener{
                context.startActivity(Intent(context,to::class.java).putExtra("title",items[position]))
            }

        GlideApp.with(holder.view.findViewById(R.id.cover_single) as ImageView)
            .load(covers[position])
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.view.findViewById(R.id.cover_single))
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}