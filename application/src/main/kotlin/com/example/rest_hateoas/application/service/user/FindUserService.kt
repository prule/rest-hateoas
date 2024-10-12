package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.application.port.`in`.user.FindUserUseCase
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import org.springframework.stereotype.Service

class FindUserService(val userRepository: UserRepository): FindUserUseCase {
    override fun findByKey(key: Key): User {
        userRepository.findOneByKey(key)?.let {
            return it
        }
        throw RuntimeException("User not found")
    }

    override fun findByUsername(username: String): User {
        userRepository.findByUsername(username)?.let {
            return it
        }
        throw RuntimeException("User not found")
    }
}