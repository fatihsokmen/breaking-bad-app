package com.github.fatihsokmen.breakingbad.data

data class CharacterModel(
    val id: Int,
    val name: String,
    val nickName: String,
    val photo: String,
    val appearance: List<String>,
    val occupation: List<String>,
    val status: String
)
