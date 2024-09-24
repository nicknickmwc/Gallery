package ru.own.gallery.Presentation

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.util.Size
import androidx.lifecycle.ViewModel
import ru.own.gallery.DataRepository.AlbumRepositoryAPI33
import ru.own.gallery.DataRepository.MediaByAlbumRepositoryImpl
import ru.own.gallery.Domain.GetAlbumUseCase
import ru.own.gallery.Domain.GetMediaByAlbumUseCase
import ru.own.gallery.Domain.MediaByAlbumRepository
import ru.own.gallery.Domain.MediaFilesModel
import java.io.File

class MediaByAlbumFragmentViewModel(): ViewModel() {

    //Объект репозитория
    private val mediaByAlbumRepositoryImpl by lazy {

        MediaByAlbumRepositoryImpl(ContextProvider.context!!)

    }

    //UseCase, выполняющий execute
    private val getMediaByAlbumUseCase by lazy {
        GetMediaByAlbumUseCase(mediaByAlbumRepositoryImpl)
    }

    var mediaFiles = ArrayList<Pair<String, Bitmap?>>()

    fun getMedia(albumName: String): Unit {
        this.mediaFiles = mediaConverter(getMediaByAlbumUseCase.execute(albumName))
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