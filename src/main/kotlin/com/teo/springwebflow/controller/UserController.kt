package com.teo.springwebflow.controller

import com.teo.springwebflow.dto.UserDTO
import com.teo.springwebflow.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: UserDTO): Mono<UserDTO> {
        return userService.create(request)
    }

    @GetMapping(path = arrayOf("/{userId}"), produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser(@PathVariable userId: String): Mono<UserDTO> {
        return userService.findById(userId)
    }
}
