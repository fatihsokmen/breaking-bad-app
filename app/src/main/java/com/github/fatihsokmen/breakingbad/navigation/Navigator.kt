package com.github.fatihsokmen.breakingbad.navigation

import com.github.fatihsokmen.breakingbad.core.livedata.LiveEvent
import com.github.fatihsokmen.breakingbad.data.CharacterModel

class Navigator {

    val onNavigateCharacterDetails = LiveEvent<CharacterModel>()

    val onNavigateAppearanceFilter = LiveEvent<Unit>()
}