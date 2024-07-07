package ru.own.gallery

import android.content.ContentResolver
import android.content.Context

abstract class MediaContentProvider(context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    abstract fun fileColumnsArray(): Array<String>

    abstract fun getAlbums()


}