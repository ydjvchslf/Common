package com.poliotmia.common

data class Profile(
    var nickName: String?,
    var oneLine: String?,
    var photo: Photo?
){
    override fun toString(): String {
        return "nickName: $nickName, oneLine: $oneLine, photo: $photo"
    }
}
