package com.example.rest_hateoas.application.port.`in`.user

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User

interface FindUserUseCase {
    fun findByKey(key: Key): User
    fun findByUsername(username: String): User
}