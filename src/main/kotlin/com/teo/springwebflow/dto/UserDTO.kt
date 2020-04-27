package com.teo.springwebflow.dto

import com.teo.springwebflow.model.User
import java.util.*

data class UserDTO(
    val id: UUID? = null,
    val enabled: Boolean? = null,
    val name: String,
    val email: String
)

fun UserDTO.toUser(id: UUID): User {
    return User(id = id, name = name, email = email)
}

fun User.toUserDTO(): UserDTO {
    return UserDTO(id, enabled, name, email)
}
