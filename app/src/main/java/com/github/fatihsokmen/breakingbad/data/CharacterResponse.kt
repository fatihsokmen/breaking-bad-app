package com.github.fatihsokmen.breakingbad.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "char_id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "nickname") val nickName: String?,
    @Json(name = "img") val photo: String?,
    @Json(name = "appearance") val appearance: List<Int>?,
    @Json(name = "occupation") val occupation: List<String>?,
    @Json(name = "status") val status: String,
)
