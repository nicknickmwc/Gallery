package ru.own.gallery.Presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.own.gallery.Domain.models.MediaFilesModel
import ru.own.gallery.Presentation.adapters.MediaFilesAdapter
import ru.own.gallery.Presentation.viewmodels.MediaByAlbumViewModel
import ru.own.gallery.R

class MediaByAlbumFragment : Fragment() {

    companion object {
        fun newInstance() = MediaByAlbumFragment()
    }

    private val viewModel: MediaByAlbumViewModel by activityViewModels()
    private val REQUEST_CODE = 100

    private lateinit var mediaFiles: MediaFilesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_media_by_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("MediaFilesByAlbumFragment", "ViewCreated")

        super.onViewCreated(view, savedInstanceState)

        if(checkPermissionOfExternalStorageRead()) {
            viewModel.getMedia(viewModel.albumName.value.toString())
            mediaFiles = viewModel.mediaFiles
        }

        Log.d("mediaFiles", mediaFiles.isNotEmpty().toString())

        if (mediaFiles.isNotEmpty()) {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_media)
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.adapter = MediaFilesAdapter(requireContext(), mediaFiles, viewModel)
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