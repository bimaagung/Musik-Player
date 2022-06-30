package com.bimaagung.musikplayer

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_musik.*

class DetailMusik : AppCompatActivity() {

    var mpintro:MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_musik)

        detailToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)

        detailToolbar.setNavigationOnClickListener{
            var i = Intent(this, MainActivity::class.java)
            this.startActivity(i)
            finish()


            mpintro = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath()+SaveMusik.patch));
            mpintro.setLooping(true)
            mpintro.start()
            Log.d("duration", mpintro.duration.toString())

        }


    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        super.onDestroy()
        //mpintro.stop()
    }
}
