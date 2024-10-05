package ru.own.gallery.Domain.usecases

import ru.own.gallery.Domain.repositories.AlbumRepository
import ru.own.gallery.Domain.models.AlbumModel

class GetAlbumUseCase(private val albumRepository: AlbumRepository) {
    fun execute(): AlbumModel {
        return albumRepository.getAlbum()
    }
}