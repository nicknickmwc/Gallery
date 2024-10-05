package ru.own.gallery.Domain.repositories

import ru.own.gallery.Domain.models.AlbumModel

interface AlbumRepository {

    fun getAlbum(): AlbumModel

}