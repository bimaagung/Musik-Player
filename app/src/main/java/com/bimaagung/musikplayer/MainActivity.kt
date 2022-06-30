package com.bimaagung.musikplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimaagung.musikplayer.Adapter.MusikAdapter
import com.bimaagung.musikplayer.Data.DataMusik
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.io.File
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE: Int = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var loading = this.indeterminateProgressDialog("Silahkan Menunggu")
        loading.apply {
            setCancelable(false)
        }
        loading.dismiss()

        var list = ArrayList<DataMusik>()

            var songList = getPlayList(Environment.getExternalStorageDirectory().getPath()+ "/Music/")
            if (songList != null) {

                for (i in 0..songList.size-1){
                    list.add(
                        DataMusik(songList.get(i).get("file_name").toString(),
                            songList.get(i).get("file_path").toString().replace("/storage/emulated/0/","/"))
                    )

                    //here you will get list of file name and file path that present in your device
                  //  Log.d("nama file ", " nama =$fileName path = $filePath")

                    var adp = MusikAdapter(this,list)
                    rv_musik.layoutManager = LinearLayoutManager(this)
                    rv_musik.adapter = adp

                }
            }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
        {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
            }else{
                Toast.makeText(this, "Permission Gagal!", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "Permission Gagal Eksternal!", Toast.LENGTH_LONG).show()

        }


    }



    internal fun getPlayList(rootPath: String): ArrayList<HashMap<String, String>>? {
        val fileList = ArrayList<HashMap<String, String>>()
        try {
            val rootFolder = File(rootPath)
            val files = rootFolder.listFiles() //here you will get NPE if directory doesn't contains  any file,handle it like this.
            for (file in files!!) {
                if (file.isDirectory) {
                    if (getPlayList(file.absolutePath) != null) {
                        fileList.addAll(getPlayList(file.absolutePath)!!)
                    }else{
                        break
                    }
                } else if (file.name.endsWith(".mp3")) {
                    val song = HashMap<String, String>()
                    song["file_path"] = file.absolutePath
                    song["file_name"] = file.nameWithoutExtension
                    fileList.add(song)
                }
            }
            return fileList
        } catch (e: Exception) {
            return null
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permission Aktif", Toast.LENGTH_LONG).show()

                }else {
                    Toast.makeText(this, "Akses gagal!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}
