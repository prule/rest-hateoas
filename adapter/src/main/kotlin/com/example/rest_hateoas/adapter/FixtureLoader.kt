package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person.PersonFixtures
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.fixtures.SampleLoader
import com.example.rest_hateoas.fixtures.UserFixtures
import com.example.rest_hateoas.fixtures.UserGroupFixtures
import org.springframework.security.crypto.password.PasswordEncoder

open class FixtureLoader(
    private val userRepository: UserRepository,
    private val userGroupRepository: UserGroupRepository,
    private val personRepository: PersonRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun load() {
        // load fixtures
        SampleLoader(userGroupRepository, UserGroupFixtures()).loadFixtures()
        SampleLoader(userRepository, UserFixtures { pwd -> passwordEncoder.encode(pwd) }).loadFixtures()
        SampleLoader(personRepository, PersonFixtures()).loadFixtures()
    }
}