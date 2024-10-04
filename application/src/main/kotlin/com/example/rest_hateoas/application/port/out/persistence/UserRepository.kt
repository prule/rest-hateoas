package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person
import com.example.rest_hateoas.domain.model.User

interface UserRepository {
    fun findOneByKey(key: Key): User?
    fun findIfExists(key: Key): User?
    fun findAll(page: Page): PageData<User>
    fun findByUsername(username: String): User?
    fun findByKey(key: Key): User?
    fun save(user: User)
    fun delete(key: Key)
}