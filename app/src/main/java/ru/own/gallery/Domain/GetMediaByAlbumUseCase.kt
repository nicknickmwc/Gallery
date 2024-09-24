package ru.own.gallery.Domain

class GetMediaByAlbumUseCase(private val mediaByAlbumRepository: MediaByAlbumRepository) {

    fun execute(albumName:String): MediaFilesModel {
        return mediaByAlbumRepository.getMedia(albumName)
    }

}