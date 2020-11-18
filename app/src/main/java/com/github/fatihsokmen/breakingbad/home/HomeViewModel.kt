package com.github.fatihsokmen.breakingbad.home

import androidx.lifecycle.*
import com.github.fatihsokmen.breakingbad.core.coroutine.CoroutineDispatcherProvider
import com.github.fatihsokmen.breakingbad.data.CharacterDomain
import com.github.fatihsokmen.breakingbad.data.CharacterInteractor
import com.github.fatihsokmen.breakingbad.data.CharacterModel
import com.github.fatihsokmen.breakingbad.data.toModel
import com.github.fatihsokmen.breakingbad.filter.AppearanceFilterModel
import com.github.fatihsokmen.breakingbad.navigation.Navigator
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val dispatchers: CoroutineDispatcherProvider,
    private val characterInteractor: CharacterInteractor,
    val navigator: Navigator,
    val lastSeason: Int
) : ViewModel() {

    private val characterDomains =
        MutableLiveData<List<CharacterDomain>>()

    private val searchFilter =
        MutableLiveData<String>()

    val seasonFilters =
        MutableLiveData((1..lastSeason).map {
            AppearanceFilterModel(it)
        })

    val characterModels =
        MediatorLiveData<List<CharacterModel>>().apply {
            addSource(characterDomains) { value = runFilters() }
            addSource(seasonFilters) { value = runFilters() }
            addSource(searchFilter) { value = runFilters() }
        }

    val selectedCharacter =
        navigator.onNavigateCharacterDetails.map {
            it
        }

    val isLoading = MutableLiveData<Boolean>()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            isLoading.value = true
            withContext(dispatchers.io) {
                characterInteractor.getCharacters()
            }.onSuccess {
                characterDomains.value = it
            }
            isLoading.value = false
        }
    }

    fun setSeasonFilters(selectedSeasons: List<Int>) {
        seasonFilters.value = seasonFilters.value?.map {
            AppearanceFilterModel(it.season, selectedSeasons.contains(it.season))
        } ?: emptyList()
    }

    fun setSearchFilter(text: String) {
        searchFilter.value = text
    }

    fun getSearchFilter() = searchFilter.value

    private fun runFilters(): List<CharacterModel> {
        val filters = seasonFilters.value?.filter { it.checked }?.map { it.season } ?: emptyList()

        val filteredBySeason = if (filters.isNotEmpty()) {
            characterDomains.value
                ?.filter { it.appearance.intersect(filters).isNotEmpty() }
                ?.map { it.toModel() }
        } else {
            characterDomains.value
                ?.map { it.toModel() }
        } ?: emptyList()

        return if (searchFilter.value.isNullOrBlank().not()) {
            filteredBySeason.filter {
                it.name.startsWith(searchFilter.value!!, true)
            }
        } else {
            filteredBySeason
        }
    }

}