package ru.own.gallery.Domain

class GetAlbumUseCase(private val albumRepository: AlbumRepository) {
    fun execute(): AlbumModel {
        return albumRepository.getAlbum()
    }
}