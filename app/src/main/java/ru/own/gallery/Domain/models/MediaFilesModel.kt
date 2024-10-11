package ru.own.gallery.Domain.models

class MediaFilesModel: ArrayList<Pair<String, String>> {
    constructor(): super()

    constructor(mediaFiles: List<Pair<String, String>>) : super(mediaFiles)
}