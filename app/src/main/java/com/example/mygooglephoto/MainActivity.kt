package com.example.mygooglephoto

import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Outline
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.lzy.okgo.OkGo
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.photos.*
import java.io.File


lateinit var myImage : MyImage

@GlideModule
class MyAppGlideModule : AppGlideModule()


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    val photosFrgm = PhotosFrgm()
    val albumsFrgm = AlbumsFrgm()
    val assistFrgm = AssistFrgm()
    val sharingFrgm = SharingFrgm()

    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        OkGo.getInstance().init(this.application)

        val filesDir = baseContext.filesDir.path
        myImage = MyImage("$filesDir/header.txt", "$filesDir/images.txt")

        requestAllPower(permissions)

        photos.setOnClickListener {
            setPhotos()
            resetAlbums()
            resetAlbums()
            resetSharing()

            if (!photosFrgm.isAdded)
            {
                fragmentManager.beginTransaction()
                    .replace(R.id.frgm_container, photosFrgm, "photos")
                    .commit()
            }
        }

        albums.setOnClickListener {
            resetPhotos()
            setAlbums()
            resetAssistant()
            resetSharing()

            if (!albumsFrgm.isAdded)
                fragmentManager.beginTransaction()
                    .replace(R.id.frgm_container, albumsFrgm, "albums")
                    .commit()
        }

        assistant.setOnClickListener {
            resetPhotos()
            resetAlbums()
            setAssistant()
            resetSharing()

            if (!assistFrgm.isAdded)
                fragmentManager.beginTransaction()
                    .replace(R.id.frgm_container, assistFrgm, "assist")
                    .commit()
        }

        sharing.setOnClickListener {
            resetPhotos()
            resetAlbums()
            resetAssistant()
            setSharing()

            if (!sharingFrgm.isAdded)
                fragmentManager.beginTransaction()
                    .replace(R.id.frgm_container, sharingFrgm, "sharing")
                    .commit()
        }


        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                myImage.loadStatus()
                Toast.makeText(baseContext,"Uploaded",Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.folders -> {
                // Handle the camera action
            }
            R.id.archive -> {

            }
            R.id.trash -> {

            }
            R.id.from_who -> {

            }
            R.id.share_with -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setPhotos() {
        var drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_photo_blue_700_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        photos.setCompoundDrawables(null, drawable, null, null)
        photos.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_down
            )
        )
    }

    private fun resetPhotos() {
        var drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_photo_black_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        photos.setCompoundDrawables(null, drawable, null, null)
        photos.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_up
            )
        )
    }

    private fun setAlbums() {
        val drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_collections_bookmark_blue_700_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        albums.setCompoundDrawables(null, drawable, null, null)
        albums.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_down
            )
        )
    }

    private fun resetAlbums() {
        val drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_collections_bookmark_black_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        albums.setCompoundDrawables(null, drawable, null, null)
        albums.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_up
            )
        )
    }

    private fun setAssistant() {
        val drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_assistant_blue_700_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        assistant.setCompoundDrawables(null, drawable, null, null)
        assistant.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_down
            )
        )
    }

    private fun resetAssistant() {
        val drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_assistant_black_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        assistant.setCompoundDrawables(null, drawable, null, null)
        assistant.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_up
            )
        )
    }

    private fun setSharing() {
        val drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_group_blue_700_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        sharing.setCompoundDrawables(null, drawable, null, null)
        sharing.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_down
            )
        )
    }

    private fun resetSharing() {
        val drawable = ContextCompat.getDrawable(
            baseContext,
            R.drawable.ic_group_black_24dp
        )
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        sharing.setCompoundDrawables(null, drawable, null, null)
        sharing.setTextColor(
            ContextCompat.getColor(
                baseContext,
                R.color.nav_bt_up
            )
        )
    }

    fun requestAllPower(permissions : Array<String>) {
        for (permission in permissions)
        {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                { }else{
                    ActivityCompat.requestPermissions(this, permissions,1)
                }
            }
        }
    }

}
