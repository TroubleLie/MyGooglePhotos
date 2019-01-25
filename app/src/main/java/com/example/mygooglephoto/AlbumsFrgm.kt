package com.example.mygooglephoto

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.albums.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.item_album.*
import kotlinx.android.synthetic.main.photos.*

class AlbumsFrgm : Fragment() {

    private var imagePaths = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater!!.inflate(R.layout.albums,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val cursor = activity.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null)

        while (cursor.moveToNext())
        {
            this.imagePaths.add(cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.Media.DATA)))
        }

        albumsView.layoutManager = GridLayoutManager( context, 3)

        albumsView.adapter = AlbumAdapter(activity.baseContext,
            arrayListOf("People"),
            arrayListOf(Pair(imagePaths[23],imagePaths[30])
        ))

        super.onViewCreated(view, savedInstanceState)
    }
}