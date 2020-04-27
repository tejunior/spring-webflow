package com.teo.springwebflow.controller

import com.teo.springwebflow.configuration.PostgresTestContextInitializer
import com.teo.springwebflow.dto.UserDTO
import com.teo.springwebflow.model.User
import com.teo.springwebflow.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [PostgresTestContextInitializer::class])
internal class UserControllerTest {
    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @Autowired
    protected lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun create() {
        webTestClient.post().uri("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(UserDTO(name = "nick", email = "emailNew@mail.com")), UserDTO::class.java)
            .exchange()
            .expectStatus()
            .isOk

        val user = userRepository.findAll().first()
        assertEquals("nick", user.name)
        assertEquals("emailNew@mail.com", user.email)
    }

    @Test
    fun findById() {
        val id = UUID.randomUUID()
        val user = User(id = id, name = "Teo", email = "teo@mail.com")
        userRepository.save(user)

        webTestClient.get().uri("/user/$id")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .json(
                """
                    {
                        "id": "$id",
                        "name": "Teo",
                        "email": "teo@mail.com"
                    }
                """.trimIndent()
            )
    }
}
