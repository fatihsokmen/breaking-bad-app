package com.github.fatihsokmen.breakingbad.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flextrade.jfixture.JFixture
import com.github.fatihsokmen.breakingbad.UnitTestCoroutineDispatcherProvider
import com.github.fatihsokmen.breakingbad.core.livedata.invoke
import com.github.fatihsokmen.breakingbad.data.CharacterDomain
import com.github.fatihsokmen.breakingbad.data.CharacterInteractor
import com.github.fatihsokmen.breakingbad.data.Result
import com.github.fatihsokmen.breakingbad.getOrAwaitValue
import com.github.fatihsokmen.breakingbad.invoke
import com.github.fatihsokmen.breakingbad.navigation.Navigator
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sut: HomeViewModel
    private lateinit var interactor: CharacterInteractor
    private lateinit var navigator: Navigator
    private lateinit var domains: List<CharacterDomain>

    private val dispatchers = UnitTestCoroutineDispatcherProvider()
    private val fixture = JFixture()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatchers.main)

        domains = listOf(
            fixture<CharacterDomain>().copy(name = "First", appearance = listOf(1, 2, 3)),
            fixture<CharacterDomain>().copy(name = "Second", appearance = listOf(1, 3, 5)),
            fixture<CharacterDomain>().copy(name = "Third", appearance = listOf(4, 5)),
        )


        interactor = mockk(relaxed = true) {
            coEvery { getCharacters() } returns Result.Success(domains)
        }
        navigator = Navigator()
        sut = HomeViewModel(
            dispatchers = dispatchers,
            characterInteractor = interactor,
            navigator = navigator,
            lastSeason = 5
        )
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model should load characters when created`() {
        coVerify {
            interactor.getCharacters()
        }
    }

    @Test
    fun `setting search filter should filter models correctly`() {
        sut.setSearchFilter("Fir")

        sut.characterModels.getOrAwaitValue() shouldHaveSize 1
    }

    @Test
    fun `setting season filters should filter models correctly`() {
        sut.setSeasonFilters(listOf(1, 3))

        sut.characterModels.getOrAwaitValue() shouldHaveSize 2
    }

    @Test
    fun `setting all filters should filter models correctly`() {
        sut.setSearchFilter("Fir")
        sut.setSeasonFilters(listOf(2))

        sut.characterModels.getOrAwaitValue() shouldHaveSize 1
    }

    @Test
    fun `models should be filtered out if no matching models found`() {
        sut.setSearchFilter("X")

        sut.characterModels.getOrAwaitValue() shouldHaveSize 0
    }

    @Test
    fun `clicked model should be selected model for detail screen`() {
        val model = sut.characterModels.getOrAwaitValue()[0]

        sut.navigator.onNavigateCharacterDetails(model)

        sut.selectedCharacter.getOrAwaitValue() shouldBe model
    }
}