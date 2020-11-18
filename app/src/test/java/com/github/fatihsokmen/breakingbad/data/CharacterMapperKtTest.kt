package com.github.fatihsokmen.breakingbad.data


import io.kotlintest.shouldBe
import org.junit.Test


class CharacterMapperKtTest {

    @Test
    fun `test model mapping`() {
        val domain = CharacterDomain(
            id = 1,
            name = "Name Surname",
            nickName = "Nickname",
            photo = "https://url/photo.jpg",
            appearance = listOf(1, 2, 4),
            occupation = listOf("Mother"),
            status = "Alive"
        )

        val actual = domain.toModel()

        actual.id shouldBe 1
        actual.name shouldBe "Name Surname"
        actual.nickName shouldBe "(Nickname)"
        actual.photo shouldBe "https://url/photo.jpg"
        actual.appearance shouldBe listOf("Season 1", "Season 2", "Season 4")
        actual.status shouldBe "Alive"
    }

    @Test
    fun `test domain mapping`() {
        val response = CharacterResponse(
            id = 2,
            name = "Name Surname",
            nickName = "Nickname",
            photo = "https://url/photo.jpg",
            appearance = listOf(1, 2, 3),
            occupation = listOf("Father"),
            status = "Alive"
        )

        val actual = response.toDomain()

        actual.id shouldBe 2
        actual.name shouldBe "Name Surname"
        actual.nickName shouldBe "Nickname"
        actual.photo shouldBe "https://url/photo.jpg"
        actual.appearance shouldBe listOf(1, 2, 3)
        actual.status shouldBe "Alive"
    }
}