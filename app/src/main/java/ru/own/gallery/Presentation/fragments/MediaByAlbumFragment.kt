package ru.own.gallery.Presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.own.gallery.Domain.models.MediaFilesModel
import ru.own.gallery.Presentation.adapters.MediaFilesAdapter
import ru.own.gallery.Presentation.viewmodels.MediaByAlbumViewModel
import ru.own.gallery.R

class MediaByAlbumFragment : Fragment() {

    private lateinit var albumsTextView: TextView
    private lateinit var imagesTextView: TextView
    private lateinit var videosTextView: TextView
    private lateinit var mediaFilesAdapter: MediaFilesAdapter

    private var selectedBottomItem = "all"


    private val viewModel: MediaByAlbumViewModel by activityViewModels()
    private val REQUEST_CODE = 100

    private lateinit var mediaFiles: MediaFilesModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_media_by_album, container, false)

        albumsTextView = view.findViewById(R.id.albums_text_view)
        imagesTextView = view.findViewById(R.id.images_text_view)
        videosTextView = view.findViewById(R.id.videos_text_view)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        if(checkPermissionOfExternalStorageRead()) {
            viewModel.getMedia(viewModel.albumName.value.toString())
            mediaFiles = viewModel.mediaFiles
        }


        setActiveStatus(albumsTextView)

        albumsTextView.setOnClickListener{

            if(selectedBottomItem != "all") {

                deactivateStatus()
                selectedBottomItem = "all"
                setActiveStatus(albumsTextView)
                updateRV(selectedBottomItem)

            }

        }

        imagesTextView.setOnClickListener{

            if(selectedBottomItem != "images") {

                deactivateStatus()
                selectedBottomItem = "images"
                setActiveStatus(imagesTextView)
                updateRV(selectedBottomItem)

            }

        }

        videosTextView.setOnClickListener{

            if(selectedBottomItem != "videos") {

                deactivateStatus()
                selectedBottomItem = "videos"
                setActiveStatus(videosTextView)
                updateRV(selectedBottomItem)

            }

        }


        if (mediaFiles.isNotEmpty()) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_media)
            mediaFilesAdapter = MediaFilesAdapter(requireContext(), mediaFiles, viewModel)
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.adapter = mediaFilesAdapter
        }


    }

    fun updateRV(typeOfMedia: String) {
        val newMediaFiles:MediaFilesModel = when (typeOfMedia) {
            "images" -> MediaFilesModel(mediaFiles.filter { it.first == "1" })
            "videos" -> MediaFilesModel(mediaFiles.filter { it.first == "2" })
            "all" -> mediaFiles
            else -> MediaFilesModel()
        }

        mediaFilesAdapter.updateMediaFiles(newMediaFiles)
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

    fun checkPermissionOfExternalStorageRead(): Boolean {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED || (
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_MEDIA_VIDEO
                    ) != PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                ),
                REQUEST_CODE
            )
            return true
        }
        else {
            return true
        }
    }

}