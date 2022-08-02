package com.poliotmia.common

data class Photo(
    var fileName: String?,
    var imageUrl: String,
){
    override fun toString(): String {
        return "fileName: $fileName, imageUrl: $imageUrl"
    }
}


