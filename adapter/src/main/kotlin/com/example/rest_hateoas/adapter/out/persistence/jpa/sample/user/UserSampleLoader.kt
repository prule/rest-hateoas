package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.user.UserJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.user.UserJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.common.Loader
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Order(2)
class UserSampleLoader(
    private val userRepository: UserRepository,
    private val userGroupRepository: UserGroupRepository,
    private val passwordEncoder: PasswordEncoder
) : Loader {

    override fun load() {
        // load object graph
        val objects = loadData("data/sample/users.json5")
        // create or update accordingly
        for (obj in objects) {
            obj.password = passwordEncoder.encode(obj.password)
            userRepository.save(UserJpaMapper.toDomain(obj)!!)
        }

        // fixture driven
        for (obj in UserFixtures.Users.entries) {
            obj.user.password = passwordEncoder.encode(obj.user.password)
            userRepository.save(obj.user)
        }
    }

    private fun loadData(path: String): Iterable<UserJpaEntity> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<UserJpaEntity>>(inputStream)
    }
}