package ru.own.gallery.DataRepository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.provider.MediaStore.Images.Thumbnails
import android.util.Log
import android.util.Size
import androidx.core.os.CancellationSignal
import java.io.File

class MediaScanner(context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver


    fun getAlbums(): HashMap<String, Pair<String, String>> {

        val albums = HashMap<String, Pair<String, String>>()
        // Получаем все папки с медиафайлами
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            arrayOf(
                MediaStore.Files.FileColumns.PARENT,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Files.FileColumns.MEDIA_TYPE
                ),
            "(${MediaStore.Files.FileColumns.MEDIA_TYPE} =? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?)",
            arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(), MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()),
            null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {

                val data = cursorGetString(MediaStore.Files.FileColumns.DATA, cursor)
                val displayName = cursorGetString(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME, cursor)
                val mediaType = cursorGetString(MediaStore.Files.FileColumns.MEDIA_TYPE, cursor)


                albums.put(displayName, Pair<String, String>(data, mediaType))

                Log.d("Курсор", displayName)
                Log.d("Курсор", data)

            }
            cursor.close()
        }
        return albums
    }

    private fun cursorGetString(fileColumn: String, cursor: Cursor ): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(fileColumn))
    }

}