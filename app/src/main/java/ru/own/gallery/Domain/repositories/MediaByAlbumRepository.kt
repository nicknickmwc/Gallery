package ru.own.gallery.Domain.repositories

import ru.own.gallery.Domain.models.MediaFilesModel

interface MediaByAlbumRepository {

    fun getMedia(albumName: String): MediaFilesModel

}