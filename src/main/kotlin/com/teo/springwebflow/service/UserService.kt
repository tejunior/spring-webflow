package com.teo.springwebflow.service

import com.teo.springwebflow.dto.UserDTO
import com.teo.springwebflow.dto.toUser
import com.teo.springwebflow.dto.toUserDTO
import com.teo.springwebflow.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun create(userDTO: UserDTO): Mono<UserDTO> {
        val newUser = userDTO.toUser(UUID.randomUUID())
        return Mono.fromCallable { userRepository.save(newUser) }
            .map { it.toUserDTO() }
            .subscribeOn(Schedulers.elastic())
    }

    fun findById(userIdString: String): Mono<UserDTO> {
        val userId = UUID.fromString(userIdString)
        return Mono.fromCallable { userRepository.findById(userId) }
            .flatMap { Mono.justOrEmpty(it) }
            .map { it.toUserDTO() }
            .subscribeOn(Schedulers.elastic())
    }
}
