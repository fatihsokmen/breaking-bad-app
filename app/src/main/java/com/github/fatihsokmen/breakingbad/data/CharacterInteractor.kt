package com.github.fatihsokmen.breakingbad.data

import java.lang.Exception

class CharacterInteractor(
    private val apiService: CharacterApiService
) {

    suspend fun getCharacters() = try {
        Result.Success(
            apiService.getCharacters().map { it.toDomain() }
        )
    } catch (e: Exception) {
        Result.Error(e)
    }
}