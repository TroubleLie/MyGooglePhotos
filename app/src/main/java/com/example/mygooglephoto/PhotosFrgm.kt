package com.example.mygooglephoto

import android.app.Fragment
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.photos.*

class PhotosFrgm : Fragment() {

    private var imagePaths = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater!!.inflate(R.layout.photos,container,false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        photosView.layoutManager = StaggeredGridLayoutManager(
            3, StaggeredGridLayoutManager.VERTICAL)
        photosView.adapter = ImageAdapter(this.context,imagePaths)

        super.onViewCreated(view, savedInstanceState)
    }
}