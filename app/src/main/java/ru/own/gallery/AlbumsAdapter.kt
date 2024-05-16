package ru.own.gallery

import android.content.Context
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AlbumsAdapter(val context: Context, private val images: List<Uri>, private val albums: HashSet<String>):
    RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>() {



    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.imageView1)
        val textView: TextView = itemView.findViewById(R.id.albumName)

    }

    //Создаем экземпляр ViewHolder, куда передаем View, передавая в контекст макет одного элемента (layout) RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_of_album, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int = images.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*
        var list = ArrayList<String>()
        for (i in albums.values) {
            if (i!=null) {
                list.add(i.toString())
            }
        }*/

        //val s: List<String> = albums.take(3)
        val s = albums.toList()
        val sString = s.joinToString(separator = ",")
        //val s = albums.size

        val bitmapThumbnail = ThumbnailUtils.createVideoThumbnail("/storage/emulated/0/Movies/VID_20230609_191332.mp4",
            MediaStore.Images.Thumbnails.MINI_KIND
        )
        holder.imageView.setImageBitmap(bitmapThumbnail)
        //holder.imageView.setImageURI(images[position])
        holder.textView.text = s[position]
        Log.d("ResultOn", sString)
        //Toast.makeText(context, albums[0].first, Toast.LENGTH_SHORT).show()
    }

}