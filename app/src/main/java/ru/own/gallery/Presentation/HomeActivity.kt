package ru.own.gallery.Presentation


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.resources.TextAppearance
import ru.own.gallery.R
import java.io.NotActiveException


class HomeActivity : AppCompatActivity() {

    private lateinit var albumsTextView: TextView
    private lateinit var imagesTextView: TextView
    private lateinit var videosTextView: TextView

    private var selectedBottomItem = "albums"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

        setActiveStatus(albumsTextView)

        albumsTextView.setOnClickListener{

            if(selectedBottomItem != "albums") {

                deactivateStatus()
                selectedBottomItem = "albums"
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

        //Передаем контекст контекст-провайдеру
        ContextProvider.provideContext(this.applicationContext)


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val albumsFragment = AlbumsFragment()
        fragmentTransaction.add(R.id.frag1, albumsFragment).commit()

    }

    fun deactivateStatus() {


        when (selectedBottomItem) {

            "albums" -> setNoActiveStatus(albumsTextView)
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