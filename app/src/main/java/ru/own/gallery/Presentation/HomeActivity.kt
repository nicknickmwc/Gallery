package ru.own.gallery.Presentation


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.own.gallery.Presentation.fragments.AlbumsFragment
import ru.own.gallery.Presentation.fragments.MediaByAlbumFragment
import ru.own.gallery.Presentation.viewmodels.AlbumsFragmentViewModel
import ru.own.gallery.Presentation.viewmodels.MediaByAlbumViewModel
import ru.own.gallery.R


class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: LinearLayout

    private val albumsViewModel: AlbumsFragmentViewModel by viewModels()
    private val mediaByAlbumViewModel: MediaByAlbumViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Передача контекста контекст-провайдеру
        ContextProvider.provideContext(this.applicationContext)


        //Создание фрагментов
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val albumsFragment = AlbumsFragment()
        val mediaByAlbumFragment = MediaByAlbumFragment()
        fragmentTransaction.add(R.id.frag1, albumsFragment).commit()

        //Отслеживание выбранного альбома
        albumsViewModel.selectedAlbum.observe(this) {name ->
            Log.d("Selected album", name)
            mediaByAlbumViewModel.albumName.value = name
                fragmentManager.beginTransaction()
                    .replace(R.id.frag1, mediaByAlbumFragment)
                    .addToBackStack(null)
                    .commit()
        }

        /////Listeners for items in bottom_nav_view

    }



}

