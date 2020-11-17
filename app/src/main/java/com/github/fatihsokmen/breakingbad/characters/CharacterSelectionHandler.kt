package com.github.fatihsokmen.breakingbad.characters

import com.github.fatihsokmen.breakingbad.data.CharacterModel

interface CharacterSelectionHandler {
    fun onCharacterClicked(character: CharacterModel)
}