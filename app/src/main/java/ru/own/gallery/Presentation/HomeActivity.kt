package ru.own.gallery.Presentation


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.own.gallery.Presentation.fragments.AlbumsFragment
import ru.own.gallery.Presentation.fragments.MediaByAlbumFragment
import ru.own.gallery.Presentation.viewmodels.AlbumsFragmentViewModel
import ru.own.gallery.Presentation.viewmodels.MediaByAlbumViewModel
import ru.own.gallery.R


class HomeActivity : AppCompatActivity() {

    private lateinit var albumsTextView: TextView
    private lateinit var imagesTextView: TextView
    private lateinit var videosTextView: TextView

    private val albumsViewModel: AlbumsFragmentViewModel by viewModels()
    private val mediaByAlbumViewModel: MediaByAlbumViewModel by viewModels()

    private var selectedBottomItem = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

        //Передача контекста контекст-провайдеру
        ContextProvider.provideContext(this.applicationContext)


        //Создание фрагментов
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val albumsFragment = AlbumsFragment()
        val mediaByAlbumFragment = MediaByAlbumFragment()
        fragmentTransaction.add(R.id.frag1, albumsFragment).commit()

        //
        albumsViewModel.selectedAlbum.observe(this) {name ->
            Log.d("Selected album", name)
            mediaByAlbumViewModel.albumName.value = name
                fragmentManager.beginTransaction()
                    .replace(R.id.frag1, mediaByAlbumFragment)
                    .addToBackStack(null)
                    .commit()
        }

        /////Listeners for items in bottom_nav_view

        setActiveStatus(albumsTextView)

        albumsTextView.setOnClickListener{

            if(selectedBottomItem != "all") {

                deactivateStatus()
                selectedBottomItem = "all"
                setActiveStatus(albumsTextView)

            }

        }

        imagesTextView.setOnClickListener{

            if(selectedBottomItem != "images") {

                deactivateStatus()
                selectedBottomItem = "images"
                setActiveStatus(imagesTextView)

            }

        }

        videosTextView.setOnClickListener{

            if(selectedBottomItem != "videos") {

                deactivateStatus()
                selectedBottomItem = "videos"
                setActiveStatus(videosTextView)

            }

        }



    }



    fun deactivateStatus() {


        when (selectedBottomItem) {

            "all" -> setNoActiveStatus(albumsTextView)
            "images" -> setNoActiveStatus(imagesTextView)
            "videos" -> setNoActiveStatus(videosTextView)

        }

    }

    fun setActiveStatus(textView: TextView) {

        val textViewPaint = textView.paint
        val textAppearance = R.style.ActiveTextViewStyle

        textViewPaint.isUnderlineText = true
        textView.paint.set(textViewPaint)
        textView.setTextAppearance(textAppearance)

    }

    fun setNoActiveStatus(textView: TextView) {

        val textViewPaint = textView.paint
        val textAppearance = R.style.NoActiveTextViewStyle

        textViewPaint.isUnderlineText = false
        textView.paint.set(textViewPaint)
        textView.setTextAppearance(textAppearance)

    }

    fun setItemTextStatus(textViewThis: TextView, textViewAnother: TextView): Unit {

        val textViewThisPaint = textViewThis.paint
        val textViewAnotherPaint = textViewAnother.paint


        val textAppearanceActive = R.style.ActiveTextViewStyle
        val textAppearanceNoActive = R.style.NoActiveTextViewStyle

        val typeface = textViewThis.typeface

        if (!typeface.isBold) {

            textViewThis.setTextAppearance(textAppearanceActive)
            textViewThisPaint.isUnderlineText = true
            textViewThis.paint.set(textViewThisPaint)

            textViewAnother.setTextAppearance(textAppearanceNoActive)
            textViewAnotherPaint.isUnderlineText = false
            textViewAnother.paint.set(textViewAnotherPaint)
        }

    }


    fun init(): Unit {

        albumsTextView = findViewById(R.id.albums_text_view)
        imagesTextView = findViewById(R.id.images_text_view)
        videosTextView = findViewById(R.id.videos_text_view)


    }



}