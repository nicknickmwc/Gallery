package ru.own.gallery

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log

class MediaScanner(context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    fun getAlbums(): HashSet<String> {

        val albums = HashSet<String>()
        // Получаем все папки с медиафайлами
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            arrayOf(
                MediaStore.Files.FileColumns.PARENT,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
                ),
            "(${MediaStore.Files.FileColumns.MEDIA_TYPE} =? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?)",
            arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(), MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()),
            null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {

                val displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME))
                val parent = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.PARENT))
                val data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                Log.d("Курсор", displayName)
                Log.d("Курсор", parent)
                Log.d("Курсор", data)
                albums.add(displayName)

            }
            cursor.close()
        }
        return albums
    }
}