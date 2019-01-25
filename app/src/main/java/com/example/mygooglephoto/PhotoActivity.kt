package com.example.mygooglephoto

import android.R.attr.*
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.albums.*
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.full_image.*


class PhotoActivity : AppCompatActivity()
{
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_image)

        full_photo.setOnClickListener {
            finish()
        }

        GlideApp.with(baseContext)
            .load(intent.getStringExtra("path"))
            .into(full_photo)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    inner class SpacesItemDecoration(private val space: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
            // Add top margin only for the first item to avoid double space between items
            //if (parent.getChildLayoutPosition(view) == 0)
            //outRect.top = space
        }
    }
}