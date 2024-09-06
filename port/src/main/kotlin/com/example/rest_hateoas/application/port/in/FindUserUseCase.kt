package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.application.domain.model.Key

interface FindUserUseCase {
    fun findByKey(key: Key): User?
    fun findByUsername(username: String): User?
}