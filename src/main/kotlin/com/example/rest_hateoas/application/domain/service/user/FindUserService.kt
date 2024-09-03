package com.example.rest_hateoas.application.domain.service.user

import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.application.port.`in`.FindUserUseCase
import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaRepository
import com.example.rest_hateoas.application.domain.model.Key
import org.springframework.stereotype.Service

@Service
class FindUserService(val userRepository: UserJpaRepository): FindUserUseCase {
    override fun findByKey(key: Key): User? {
        return userRepository.findByKey(key)
    }

    override fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
}