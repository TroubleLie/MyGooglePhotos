package com.example.mygooglephoto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivities
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AlbumAdapter(val context: Context, val items : List<String>,
                   val covers : ArrayList<Pair<String,String>>)
    : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_album,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: AlbumAdapter.ViewHolder,
        position: Int
    ) {
        holder.view.findViewById<TextView>(R.id.album_name).text = items[position]

        holder.view.findViewById<LinearLayout>(R.id.label_album)
            .setOnClickListener{
                context.startActivity(Intent(context,AlbumActivity::class.java))
            }

        GlideApp.with(holder.view.findViewById(R.id.cover_one) as ImageView)
            .load(covers[position].first)
            .skipMemoryCache(true)
            .thumbnail(0.25f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.view.findViewById(R.id.cover_one))

        GlideApp.with(holder.view.findViewById(R.id.cover_two) as ImageView)
            .load(covers[position].second)
            .skipMemoryCache(true)
            .thumbnail(0.25f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.view.findViewById(R.id.cover_two))
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}