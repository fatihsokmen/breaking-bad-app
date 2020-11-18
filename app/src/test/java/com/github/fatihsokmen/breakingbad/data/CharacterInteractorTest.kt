package com.github.fatihsokmen.breakingbad.data

import com.flextrade.jfixture.JFixture
import com.github.fatihsokmen.breakingbad.invoke
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class CharacterInteractorTest {

    private lateinit var sut: CharacterInteractor
    private lateinit var apiService: CharacterApiService

    private val fixture = JFixture()

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        sut = CharacterInteractor(apiService)
    }

    @Test
    fun `interactor should call service to fetch character list`() = runBlockingTest {
        sut.getCharacters()

        coVerify {
            apiService.getCharacters()
        }
    }

    @Test
    fun `interactor should return successful result when api succeeds`() = runBlockingTest {
        val data = fixture<List<CharacterResponse>>()
        coEvery {
            apiService.getCharacters()
        } returns data

        val actual = sut.getCharacters()

        actual.shouldBeTypeOf<Result.Success<List<CharacterDomain>>>()
        actual.getOrNull() shouldBe data
    }

    @Test
    fun `interactor should return error result when api service fails`() = runBlockingTest {
        val exceptionMessage = fixture<String>()
        coEvery {
            apiService.getCharacters()
        } throws Exception(exceptionMessage)

        val actual = sut.getCharacters()

        actual.shouldBeTypeOf<Result.Error<String>>()
        (actual as Result.Error).throwable.message shouldBe  exceptionMessage
    }
}