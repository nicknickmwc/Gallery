package ru.own.gallery.Presentation


import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Size
import androidx.lifecycle.ViewModel
import ru.own.gallery.DataRepository.AlbumRepositoryImpl
import ru.own.gallery.Domain.GetAlbumUseCase
import java.io.File

class AlbumsFragmentViewModel(): ViewModel() {

    //Объект репозитория
    private val albumRepositoryImpl by lazy {

        AlbumRepositoryImpl(ContextProvider.context!!)

    }

    //UseCase, выполняющий execute
    private val getAlbumUseCase by lazy {
        GetAlbumUseCase(albumRepositoryImpl)
    }

    //Альбомы, в соответствующем виде для передачи
    lateinit var albums: HashMap<String, Bitmap?>

    //Присваиваем albums необходимое значение
    fun albumsGet(): HashMap<String, Bitmap?>  {
        this.albums = albumsConverter(getAlbumUseCase.execute())
        return this.albums
    }

    fun albumsConverter(albums: HashMap<String, Pair<String, String>>): HashMap<String, Bitmap?> {

        var newAlbums = HashMap<String, Bitmap?>()
        var thumbnail: Bitmap? = null


        for(item in albums) {

            //Название альбома
            val key = item.key
            //Пара 'путь к файлу' - 'формат'
            val pair = item.value

            //'Формат' (создаем для каждого формата свою миниатюру)
            when (pair.second) {

                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString() -> {
                    thumbnail = ThumbnailUtils.createImageThumbnail(File(pair.first), Size(512,512), CancellationSignal())
                }

                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString() -> {
                    thumbnail = ThumbnailUtils.createVideoThumbnail(File(pair.first), Size(512,512), CancellationSignal())
                }

            }
            newAlbums.put(key, thumbnail)
        }

        return newAlbums
    }

}