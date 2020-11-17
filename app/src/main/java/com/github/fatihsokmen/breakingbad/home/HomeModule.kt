package com.github.fatihsokmen.breakingbad.home

import com.github.fatihsokmen.breakingbad.data.CharacterApiService
import com.github.fatihsokmen.breakingbad.data.CharacterInteractor
import com.github.fatihsokmen.breakingbad.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule: Module = module {

    factory<CharacterApiService> {
        get<Retrofit>().create(CharacterApiService::class.java)
    }

    factory {
        CharacterInteractor(apiService = get())
    }

    viewModel {
        HomeViewModel(
            characterInteractor = get(),
            navigator = Navigator(),
            lastSeason = 5
        )
    }

}