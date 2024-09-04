package ru.own.gallery.DataRepository

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import ru.own.gallery.Domain.AlbumModel
import ru.own.gallery.Domain.AlbumRepository

class AlbumRepositoryImpl(private val context: Context): AlbumRepository {


    private val contentResolver:ContentResolver = context.contentResolver

    override fun getAlbum(): AlbumModel {
        val albums = AlbumModel()
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
    private fun cursorGetString(fileColumn: String, cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(fileColumn))
    }
}