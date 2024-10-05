package ru.own.gallery.DataRepository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import ru.own.gallery.Domain.models.AlbumModel
import ru.own.gallery.Domain.repositories.AlbumRepository

class AlbumRepositoryAPI33(private val context: Context): AlbumRepository {

    private val contentResolver: ContentResolver = context.contentResolver
    private val images = ArrayList<String>() //mediaType is 1
    private val videos = ArrayList<String>() //mediaType is 2


    override fun getAlbum(): AlbumModel {

        val albums = AlbumModel()

        val projectionImages = arrayOf(

            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,


        )

        val projectionVideos = arrayOf(

            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,


        )

        val imagesCursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projectionImages,
            null,
            null,
            null
        )

        val videosCursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projectionVideos,
            null,
            null,
            null
        )

        if (imagesCursor != null) {

            while (imagesCursor.moveToNext()) {

                val data = cursorGetString(MediaStore.Images.Media.DATA, imagesCursor)
                val displayName = cursorGetString(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, imagesCursor)
                val mediaType = "1"

                albums.put(displayName, Pair<String, String>(data, mediaType))
                images.add(data)

            }

            imagesCursor.close()

        }

        if (videosCursor != null) {

            while (videosCursor.moveToNext()) {

                val data = cursorGetString(MediaStore.Video.Media.DATA, videosCursor)
                val displayName = cursorGetString(MediaStore.Video.Media.BUCKET_DISPLAY_NAME, videosCursor)
                val mediaType = "2"

                albums.put(displayName, Pair<String, String>(data, mediaType))
                videos.add(data)

            }
            videosCursor.close()
        }

        return albums

    }

    private fun cursorGetString(fileColumn: String, cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(fileColumn))
    }

}