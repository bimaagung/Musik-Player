package com.bimaagung.musikplayer.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bimaagung.musikplayer.Data.DataMusik
import com.bimaagung.musikplayer.DetailMusik
import com.bimaagung.musikplayer.R
import com.bimaagung.musikplayer.SaveMusik
import kotlinx.android.synthetic.main.musik_row.view.*

class MusikAdapter (var context: Context, var list: ArrayList<DataMusik>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.musik_row, parent, false)
        return ListMusik(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListMusik).show(list[position].judulMusik)


        var option = 0

        holder.itemView.ly_song.setOnClickListener{

            try
            {
            var mpintro = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getPath()+SaveMusik.patch));
            mpintro.stop()
            }
            catch (e: NullPointerException) {
                option = 1
            }
            finally {
                if(option == 1)
                {
                    println("Error")
                }
            }

            SaveMusik.patch = list[position].filePatch

            var i = Intent(context, DetailMusik::class.java)
            (context as Activity).finish()
            context.startActivity(i)
        }
    }

    class ListMusik(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        fun show(judulMusik:String)
        {
            itemView.tx_label.text = judulMusik
        }

    }
}
