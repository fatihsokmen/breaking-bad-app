package com.github.fatihsokmen.breakingbad.data


const val SEASON = "Season"

fun CharacterDomain.toModel() =
    CharacterModel(
        id = id,
        name = name,
        nickName = "($nickName)",
        photo = photo,
        appearance = appearance.map { "$SEASON $it" },
        occupation = occupation.map { "- $it" },
        status = status
    )

fun CharacterResponse.toDomain() =
    CharacterDomain(
        id = id,
        name = name.orEmpty(),
        nickName = nickName.orEmpty(),
        photo = photo.orEmpty(),
        appearance = appearance ?: emptyList(),
        occupation = occupation ?: emptyList(),
        status = status
    )