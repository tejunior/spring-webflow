package com.teo.springwebflow.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "USER_LOGIN")
data class User(
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "id", columnDefinition = "UUID")
    val id: UUID,

    @NotBlank
    @Column(name = "enabled")
    val enabled: Boolean = true,

    @NotBlank
    @Column(name = "name")
    val name: String,

    @NotBlank
    @Column(name = "email")
    @Email
    val email: String,

    @NotBlank
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @NotBlank
    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: ZonedDateTime = ZonedDateTime.now()
)
