package com.github.fatihsokmen.breakingbad.filter

class AppearanceFilterModel(
    val season: Int,
    val checked: Boolean = false
) {
    val text
        get() = "Season $season"
}