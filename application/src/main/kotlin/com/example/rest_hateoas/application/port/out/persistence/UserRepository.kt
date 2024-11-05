package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.port.`in`.Filter
import com.example.rest_hateoas.application.port.`in`.user.UserFilter
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import org.springframework.transaction.annotation.Transactional

@Transactional
interface UserRepository : Repository<User, UserFilter> {

    fun findByUsername(username: String): User?
}