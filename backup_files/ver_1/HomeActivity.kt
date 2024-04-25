package ru.own.gallery


import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.security.Permission
import kotlin.collections.HashSet

class HomeActivity : AppCompatActivity() {

    private val EXTERNAL_STORAGE_R_CODE: Int = 44
    private val REQUEST_EXTERNAL_STORAGE_CODE = 100
    //private lateinit var foldersAndFiles:List<Pair<String, List<String>>>
    //private lateinit var albums: HashMap<String, Bitmap?>
    private lateinit var albums: HashSet<String>
    private val PermissionTag = "onRequestPermissionsResult"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //Запрашиваем разрешение на чтение хранилища
        if (checkPermissionOfExternalStorageRead()) {
            //Создание экземляра MediaScanner для получения медиафайлов
            val scanner = MediaScanner(applicationContext)
            albums = scanner.getAlbums()
        }

        //Создание экземляра MediaScanner для получения медиафайлов
        //val scanner = MediaScanner(applicationContext)
        //foldersAndFiles = scanner.getMediaFoldersAndFiles()

        //Создаем лист с ссылками на изображения
        val imagesUri = mutableListOf<Uri>()
        for (i in 1..albums.size) {
            (imagesUri.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.example_image1)))
        }
        val imagesUriList = imagesUri.toList()


        //RecyclerView start
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = AlbumsAdapter(this, imagesUriList, albums)
        //RecyclerView end


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Разрешение предоставлено, можно продолжать
                    // Здесь вы можете выполнить действия, которые требуют доступа к внешнему хранилищу
                    //Создание экземляра MediaScanner для получения медиафайлов
                    //val scanner = MediaScanner(applicationContext)
                    //foldersAndFiles = scanner.getMediaFoldersAndFiles()
                    Log.d(PermissionTag, "Разрешение было предоставлено")
                } else {
                    // Разрешение не предоставлено
                    // Здесь вы можете уведомить пользователя о том, что некоторые функции недоступны без доступа к внешнему хранилищу

                }
            }
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