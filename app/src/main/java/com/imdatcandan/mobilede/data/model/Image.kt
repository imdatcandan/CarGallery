package com.imdatcandan.mobilede.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val uri: String,
){
    fun getThumbnailUrl(): String =
        "https://" + uri.removePrefix("m.mobile.de/yams-proxy/") + "?rule=mo-640.jpg"

    fun getLargeUrl(): String =
        "https://" + uri.removePrefix("m.mobile.de/yams-proxy/") + "?rule=mo-1600.jpg"
}