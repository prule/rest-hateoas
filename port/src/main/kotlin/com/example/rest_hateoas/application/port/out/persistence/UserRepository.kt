package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.application.domain.model.Key
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserRepository {
    fun findAll(pageable: Pageable): Page<User>
    fun findByUsername(username: String): User?
    fun findByKey(key: Key): User?
    fun save(user: User)
}