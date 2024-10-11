package ru.own.gallery.Presentation.adapters

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.own.gallery.Domain.models.MediaFilesModel
import ru.own.gallery.R
import java.io.File
import com.bumptech.glide.Glide
import ru.own.gallery.Presentation.viewmodels.MediaByAlbumViewModel

class MediaFilesAdapter(private val context: Context, private var mediaFiles: MediaFilesModel,
                        private val mediaByAlbumViewModel: MediaByAlbumViewModel
    ): RecyclerView.Adapter<MediaFilesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById<ImageView>(R.id.image_view_media_files_rv)

    }

    fun updateMediaFiles(newMediaFiles: MediaFilesModel) {
        mediaFiles = newMediaFiles
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_for_media_files_rv, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = mediaFiles.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val mediaPath = mediaFiles[position].second

        //holder.imageView.setImageBitmap(createBitmap(mediaFiles, position))
        Glide.with(context)
            .load(File(mediaPath))
            .override(256,256)
            .into(holder.imageView)

    }

}