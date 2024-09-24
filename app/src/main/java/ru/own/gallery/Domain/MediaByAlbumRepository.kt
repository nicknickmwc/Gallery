package ru.own.gallery.Domain

interface MediaByAlbumRepository {

    fun getMedia(albumName: String): MediaFilesModel

}