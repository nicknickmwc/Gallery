package ru.own.gallery.Domain.usecases

import ru.own.gallery.Domain.repositories.MediaByAlbumRepository
import ru.own.gallery.Domain.models.MediaFilesModel

class GetMediaByAlbumUseCase(private val mediaByAlbumRepository: MediaByAlbumRepository) {

    fun execute(albumName:String): MediaFilesModel {
        return mediaByAlbumRepository.getMedia(albumName)
    }

}