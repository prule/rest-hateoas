package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.Loader
import com.example.rest_hateoas.person.Person
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
@Order(2)
class UserSampleLoader(
    private val userRepository: UserRepository,
    private val userGroupRepository: UserGroupRepository,
    private val passwordEncoder: PasswordEncoder
) : Loader {

    override fun load() {
        createOrUpdate("data/sample/users.json5") { obj: User -> createOrUpdateUser(obj) }
    }

    private fun createOrUpdate(path: String, runnable: Function<User, User>) {
        // load object graph
        val objects = loadData(path)
        // create or update accordingly
        for (obj in objects) {
            runnable.apply(obj)
        }
    }

    private fun createOrUpdateUser(newUser: User): User {
        val newGroups = newUser.groups.map { userGroupRepository.findByKey(it.key) }.filterNotNull()

        val user: User = userRepository.findByKey(newUser.key) ?: newUser
        user.password = passwordEncoder.encode(newUser.password)
        user.firstName = newUser.firstName
        user.lastName = newUser.lastName
        user.groups.clear()
        user.groups.addAll(newGroups)

        userRepository.save(user)
        return user
    }

    private fun loadData(path: String): Iterable<User> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path) ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<User>>(inputStream)
    }
}