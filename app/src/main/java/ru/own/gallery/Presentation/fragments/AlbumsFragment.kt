package ru.own.gallery.Presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.own.gallery.Domain.models.AlbumModel
import ru.own.gallery.Presentation.adapters.AlbumsAdapter
import ru.own.gallery.Presentation.viewmodels.AlbumsFragmentViewModel
import ru.own.gallery.R


class AlbumsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val REQUEST_CODE = 100

    //private val albumsViewModel: AlbumsFragmentViewModel by viewModels()
    private val albumsActivityViewModel: AlbumsFragmentViewModel by activityViewModels()

    //Список альбомов, конвертированный в вид HashMap<String, Bitmap?>
    lateinit var albums: AlbumModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (checkPermissionOfExternalStorageRead()) {
            permissionsIsGranted()
        }

        if (albums.isNotEmpty()) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = AlbumsAdapter(requireContext(), albums, albumsActivityViewModel)
            }

        //albumsActivityViewModel.getSelectedALbum.observe(viewLifecycleOwner) {}

    }


    fun permissionsIsGranted() {
        this.albums = albumsActivityViewModel.albumsGet()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Разрешение успешно получено, можно продолжить работу с медиа
            permissionsIsGranted()
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