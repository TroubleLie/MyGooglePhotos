package com.example.mygooglephoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import okhttp3.*
import okio.BufferedSink
import java.io.ByteArrayOutputStream
import java.io.File

class MyImage (val headerPath: String, val imagesPath:String){
    var images = ArrayList<ArrayList<String>>()
    var idtfdImgs = ArrayList<ArrayList<String>>()
    var newImgs = ArrayList<ArrayList<String>>()
    var hasEdited = false
    var header = ArrayList<String>(2)
    var status = Pair(File(headerPath), File(imagesPath))

    fun loadStatus()
    {
        status.second.forEachLine {
            images.add(arrayListOf(it.split("@").first(),it.split("@").last()))
        }
        status.first.forEachLine { header.add(it) }

        classifier()

        images.forEach{Log.d("images",it.first() + " " + it.last())}
        idtfdImgs.forEach{Log.d("idtfdImgs",it.first() + " " + it.last())}
    }

    fun classifier()
    {
        var temp = images
        while (!temp.isEmpty() && idtfdImgs.size<=temp.size)
        {
            val content = ArrayList<String>()
            val bound = temp.first().last()
            temp.filter{it.last() == bound}
                .forEach { content.add(it.first()); temp.remove(it) }
            idtfdImgs.add(content)
        }
    }


    fun saveStatus()
    {
        if (!hasEdited) return

        for (image in newImgs)
        {
            val string = image[0] + " " +image[1] + "\n"
            status.second.appendText(string)
        }

        status.first.writeText(header[0] + "\n" + header[1])

        hasEdited = false
    }

    fun pullStatus(url: String)
    {
        var result = String()
        OkGo.get<String>(url)
            .tag(this)
            .cacheKey("cacheKey")
            .cacheMode(CacheMode.DEFAULT)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    result = response!!.body()
                }
            })

        var newHeader = result.split("#")[0]
        header[1] = header[0]
        header[0] = newHeader

        var imgs = result.split("#")[1]

        for (field in imgs.split(";"))
        {
            val name = field.split(' ')[0]
            val label = field.split(' ')[1]
            for (image in newImgs)
                if (name == image[0])
                    image[1] = label
        }
        for (image in newImgs) {
            images.add(image)
        }

        hasEdited = true

        saveStatus()
    }

    fun pushStatus(url: String, imagePaths: ArrayList<String>)
    {
        for (imagePath in imagePaths) {
            clientOkHttp(
                url, btimapToBtyes(BitmapFactory.decodeFile(imagePath)))
        }

    }

    fun btimapToBtyes(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun clientOkHttp(POST_IMAGE:String, FILE_IMAGE: ByteArray){
        val client = OkHttpClient()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", "Trouble")
            .addFormDataPart("myfile","Naruto.jpg",
                ProgressRequestBody(MediaType.parse("image/*"),
                    FILE_IMAGE))
            .build()

        val request = Request.Builder()
            .url(POST_IMAGE)
            .post(requestBody)
            .build()
        val call = client.newCall(request)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                Log.i("kang","asdjlajslkdjasd=21="+body!!.string())
                //val string = body!!.string()
            }
            response.close()
        }catch (e:Exception){
            Log.i("kang","error="+e.toString())
        }
    }

    class ProgressRequestBody(private val contentType: MediaType?,
                              private val file: ByteArray) : RequestBody() {
        override fun contentType(): MediaType? {
            return contentType
        }
        override fun contentLength(): Long {
            return file.size.toLong()
        }
        override fun writeTo(sink: BufferedSink) {
            try {
                sink.write(file)
            } catch (e: Exception){}
        }
    }
}