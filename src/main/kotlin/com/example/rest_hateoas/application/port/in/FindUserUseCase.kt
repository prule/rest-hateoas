package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaEntity
import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.common.Key

interface FindUserUseCase {
    fun findByKey(key: Key): User?
    fun findByUsername(username: String): User?
}