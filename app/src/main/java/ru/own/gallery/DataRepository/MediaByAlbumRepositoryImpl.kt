package ru.own.gallery.DataRepository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import ru.own.gallery.Domain.repositories.MediaByAlbumRepository
import ru.own.gallery.Domain.models.MediaFilesModel

class MediaByAlbumRepositoryImpl(context: Context): MediaByAlbumRepository {

    private val contentResolver: ContentResolver = context.contentResolver

    override fun getMedia(albumName: String): MediaFilesModel {

        Log.d("MediaByAlbumRepositoryImpl", "getMediaWasCalled")

        val mediaFiles = MediaFilesModel()

        val projectionImages = arrayOf(

            MediaStore.Images.Media.DATA,
            //MediaStore.Images.Media.BUCKET_DISPLAY_NAME,


            )

        val projectionVideos = arrayOf(

            MediaStore.Video.Media.DATA,
            //MediaStore.Video.Media.BUCKET_DISPLAY_NAME,


            )

        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = '$albumName'"
        Log.d("MediaByAlbumRepository", "selection = $selection")

        val imagesCursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projectionImages,
            selection,
            null,
            null
        )

        val videosCursor = contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projectionVideos,
            selection   ,
            null,
            null
        )

        if (imagesCursor != null) {

            Log.d("MediaByAlbumRepositoryImpl", "imagesCursorIsNull - ${imagesCursor!=null}")
            Log.d("MediaByAlbumRepositoryImpl", "albumName = $albumName")

            while (imagesCursor.moveToNext()) {

                val data = cursorGetString(MediaStore.Images.Media.DATA, imagesCursor)
                //val displayName = cursorGetString(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, imagesCursor)
                val mediaType = "1"

                mediaFiles.add(Pair(mediaType, data))



            }

            imagesCursor.close()

        }

        if (videosCursor != null) {

            Log.d("MediaByAlbumRepositoryImpl", "videosCursorIsNull - ${videosCursor!=null}")

            while (videosCursor.moveToNext()) {

                val data = cursorGetString(MediaStore.Video.Media.DATA, videosCursor)
                //val displayName = cursorGetString(MediaStore.Video.Media.BUCKET_DISPLAY_NAME, videosCursor)
                val mediaType = "2"

                mediaFiles.add(Pair(mediaType, data))


            }
            videosCursor.close()
        }

        return mediaFiles

    }

    private fun cursorGetString(fileColumn: String, cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(fileColumn))
    }

}