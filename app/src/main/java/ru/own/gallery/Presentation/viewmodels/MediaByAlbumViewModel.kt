package ru.own.gallery.Presentation.viewmodels

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.util.Log
import android.util.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.own.gallery.DataRepository.MediaByAlbumRepositoryImpl
import ru.own.gallery.Domain.usecases.GetMediaByAlbumUseCase
import ru.own.gallery.Domain.models.MediaFilesModel
import ru.own.gallery.Presentation.ContextProvider
import java.io.File

class MediaByAlbumViewModel(): ViewModel() {

    val albumName = MutableLiveData<String>()

    fun setSelectedAlbum(album: String) {
        Log.d("ViewModel", "Setting completed")
        albumName.value = album
    }


    //Объект репозитория
    private val mediaByAlbumRepositoryImpl by lazy {

        MediaByAlbumRepositoryImpl(ContextProvider.context!!)

    }

    //UseCase, выполняющий execute
    private val getMediaByAlbumUseCase by lazy {
        GetMediaByAlbumUseCase(mediaByAlbumRepositoryImpl)
    }

    var mediaFiles = MediaFilesModel()

    fun getMedia(albumName: String): Unit {
        this.mediaFiles = getMediaByAlbumUseCase.execute(albumName)
    }

    private fun mediaConverter(mediaFiles: MediaFilesModel):  ArrayList<Pair<String, Bitmap?>> {

        val newMediaFiles = ArrayList<Pair<String, Bitmap?>>()
        var thumbnail: Bitmap? = null

        for(item in mediaFiles) {

            when (item.first) {
                "1" -> {
                    thumbnail = ThumbnailUtils.createImageThumbnail(File(item.second), Size(512,512), CancellationSignal())
                }
                "2" -> {
                    thumbnail = ThumbnailUtils.createVideoThumbnail(File(item.second), Size(512,512), CancellationSignal())
                }
            }

            newMediaFiles.add(Pair(item.second, thumbnail))

        }

        return newMediaFiles

    }

}