package com.example.mygooglephoto

import android.content.Context
import android.support.annotation.BinderThread
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import android.util.Xml
import org.xmlpull.v1.XmlPullParser



@GlideModule
class MyAppGlideModule : AppGlideModule()

class ImageAdapter(val context: Context, val items : List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_image,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ImageAdapter.ViewHolder,
        position: Int
    ) {
        GlideApp.with(holder.view.findViewById(R.id.item) as ImageView)
            .load(items[position])
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop().override(400,400)
            .into(holder.view.findViewById(R.id.item))
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}