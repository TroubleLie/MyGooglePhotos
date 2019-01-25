package com.example.mygooglephoto

import android.app.Fragment
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater!!.inflate(R.layout.albums,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        if (!myImage.idtfdImgs.isEmpty())
        {
            albumsView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            albumsView.addItemDecoration(SpacesItemDecoration(8))

            albumsView.adapter = AlbumAdapter(activity.baseContext,
                arrayListOf("People"),
                arrayListOf(Pair(
                    myImage.idtfdImgs.first().first(),
                    myImage.idtfdImgs.last().first())),
                AlbumActivity())
        }

        super.onViewCreated(view, savedInstanceState)
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