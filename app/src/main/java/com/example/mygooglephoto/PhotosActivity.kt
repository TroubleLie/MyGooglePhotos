package com.example.mygooglephoto

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.albums.*
import android.R.attr.top
import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.left
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class PhotosActivity : AppCompatActivity()
{

    private var imagePaths = ArrayList<String>()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        setSupportActionBar(toolbar2)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        et_title.setText(intent.getStringExtra("title"))
        et_title.focusable = View.NOT_FOCUSABLE

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null)

        while (cursor.moveToNext())
        {
            this.imagePaths.add(cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.Media.DATA)))
        }

        albumView.layoutManager = GridLayoutManager(baseContext, 4)
        albumView.addItemDecoration(SpacesItemDecoration(8))
        albumView.adapter = ImageAdapter(baseContext, myImage.idtfdImgs[intent.getStringExtra("label").toInt()], PhotoActivity())
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