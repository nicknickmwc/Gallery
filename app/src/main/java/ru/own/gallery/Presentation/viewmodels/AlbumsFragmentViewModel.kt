package ru.own.gallery.Presentation.viewmodels


import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.own.gallery.DataRepository.AlbumRepositoryAPI33
import ru.own.gallery.Domain.usecases.GetAlbumUseCase
import ru.own.gallery.Presentation.ContextProvider
import java.io.File

class AlbumsFragmentViewModel(): ViewModel() {

    val selectedAlbum = MutableLiveData<String>()

    /*val getSelectedALbum: LiveData<String>
        get() = getSelectedALbum
*/
    fun setSelectedAlbum(album: String) {
        Log.d("ViewModel", "Setting completed")
        selectedAlbum.value = album
    }

    //Объект репозитория
    private val albumRepositoryImpl by lazy {

        AlbumRepositoryAPI33(ContextProvider.context!!)

    }

    //UseCase, выполняющий execute
    private val getAlbumUseCase by lazy {
        GetAlbumUseCase(albumRepositoryImpl)
    }

    //Альбомы, в соответствующем виде для передачи
    lateinit var albums: HashMap<String, Bitmap?>

    //Присваиваем albums необходимое значение
    fun albumsGet(): HashMap<String, Bitmap?>  {
        this.albums = albumsConverterAPI33(getAlbumUseCase.execute())
        return this.albums
    }

    fun albumsConverterAPI33(albums: HashMap<String, Pair<String, String>>): HashMap<String, Bitmap?> {
        var newAlbums = HashMap<String, Bitmap?>()
        var thumbnail: Bitmap? = null

        for(item in albums) {

            //Название альбома
            val key = item.key
            //Пара 'путь к файлу' - 'формат'
            val pair = item.value

            //'Формат' (создаем для каждого формата свою миниатюру)
            when (pair.second) {

                "1" -> {
                    thumbnail = ThumbnailUtils.createImageThumbnail(File(pair.first), Size(512,512), CancellationSignal())
                }

                "2" -> {
                    thumbnail = ThumbnailUtils.createVideoThumbnail(File(pair.first), Size(512,512), CancellationSignal())
                }

            }
            newAlbums.put(key, thumbnail)
        }

        return newAlbums


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