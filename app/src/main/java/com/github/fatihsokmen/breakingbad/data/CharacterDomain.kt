package com.github.fatihsokmen.breakingbad.data

data class CharacterDomain(
    val id: Int,
    val name: String,
    val nickName: String,
    val photo: String,
    val appearance: List<Int>,
    val occupation: List<String>,
    val status: String
)
