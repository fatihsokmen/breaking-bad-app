package com.github.fatihsokmen.breakingbad.data

import com.github.fatihsokmen.breakingbad.R

const val STATUS_ALIVE = "Alive"
const val SEASON = "Season"

fun CharacterDomain.toModel() =
    CharacterModel(
        id = id,
        name = name,
        nickName = "($nickName)",
        photo = photo,
        appearance = appearance.map { "$SEASON $it" },
        occupation = occupation.map { "- $it" },
        status = status,
        statusTint = if (status.equals(STATUS_ALIVE, true))
            R.color.teal_700
        else
            R.color.purple_200
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