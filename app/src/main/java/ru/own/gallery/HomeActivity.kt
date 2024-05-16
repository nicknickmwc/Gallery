package ru.own.gallery


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class HomeActivity : AppCompatActivity() {

    private val EXTERNAL_STORAGE_R_CODE: Int = 44
    private val REQUEST_EXTERNAL_STORAGE_CODE = 100
    //private lateinit var foldersAndFiles:List<Pair<String, List<String>>>
    //private lateinit var albums: HashMap<String, Bitmap?>
    private lateinit var albums: HashMap<String, Pair<String, String>>
    private val PermissionTag = "onRequestPermissionsResult"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //Запрашиваем разрешение на чтение хранилища
        if (checkPermissionOfExternalStorageRead()) {
            getAlbums()
        }


        //Создаем лист с ссылками на изображения
        /*val imagesUri = mutableListOf<Uri>()
        for (i in 1..albums.size) {
            (imagesUri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.example_image1)))
        }
        val imagesUriList = imagesUri.toList()*/


        //RecyclerView start
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = AlbumsAdapter(this, albumsConverter())
        //RecyclerView end


    }

    //Создание map с содержимым навзаний альбомов, пути к визитному медиафайлу и его форматом
    fun getAlbums(): Unit {
        val scanner = MediaScanner(this)
        albums = scanner.getAlbums()
    }

    //Создание нового map с названием альбома в качестве ключа и его миниатюрой
    fun albumsConverter(): HashMap<String, Bitmap?> {

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("OnRequest", "Функция запущена")
        when (requestCode) {
            EXTERNAL_STORAGE_R_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getAlbums()
                    Log.d(PermissionTag, "Разрешение было предоставлено")

                } else {

                    // Разрешение не предоставлено
                    Log.d(PermissionTag, "Разрешение не было предоставлено")

                }
            }
            //else -> Log.d("Неправильный код", requestCode.toString())
        }
    }

    fun checkPermissionOfExternalStorageRead(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
                return true
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), EXTERNAL_STORAGE_R_CODE)
                return false
            }

        }
        return TODO("Provide the return value")
    }



    fun init(): Unit {

    }

}