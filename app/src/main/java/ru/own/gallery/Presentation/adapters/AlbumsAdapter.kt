package ru.own.gallery.Presentation.adapters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.own.gallery.Presentation.viewmodels.AlbumsFragmentViewModel
import ru.own.gallery.R


class AlbumsAdapter(val context: Context, private val albums: HashMap<String, Bitmap?>,val viewModel: AlbumsFragmentViewModel):
    RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>() {




    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        val imageView: ImageView = itemView.findViewById(R.id.imageView1)
        val textView: TextView = itemView.findViewById(R.id.albumName)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        init {
            cardView.setOnClickListener {
                viewModel.setSelectedAlbum(albums.keys.toList()[adapterPosition])
            }
        }




    }

    //Создаем экземпляр ViewHolder, куда передаем View, передавая в контекст макет одного элемента (layout) RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_of_album, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int = albums.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*
        var list = ArrayList<String>()
        for (i in albums.values) {
            if (i!=null) {
                list.add(i.toString())
            }
        }*/

        //val s: List<String> = albums.take(3)
        //val s = albums.toList()
        //val sString = s.joinToString(separator = ",")
        //val s = albums.size

        val key = albums.keys.toList()[position]

        //holder.imageView.setImageURI(images[position])
        holder.textView.text = key
        holder.imageView.setImageBitmap(albums[key])
        Log.d("ResultOn", "sString")

        /*val intent = Intent(context, AlbumActivity::class.java)
        context.startActivity(intent)*/


        //Toast.makeText(context, albums[0].first, Toast.LENGTH_SHORT).show()
    }

}