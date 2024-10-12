package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.adapter.out.persistence.jpa.user.UserJpaRepository
import com.example.rest_hateoas.application.port.`in`.person.PersonCreateUseCase
import com.example.rest_hateoas.application.port.`in`.person.PersonFindUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.application.service.person.*
import com.example.rest_hateoas.application.service.user.FindUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class Config {

    @Bean
    fun findUserService(userJpaRepository: UserJpaRepository): FindUserService {
        return FindUserService(userJpaRepository)
    }

    @Bean
    fun personCreateService(personRepository: PersonRepository): PersonCreateUseCase {
        return PersonCreateService(personRepository)
    }

    @Bean
    fun personDeleteService(personRepository: PersonRepository): PersonDeleteService {
        return PersonDeleteService(personRepository)
    }

    @Bean
    fun personFindService(personRepository: PersonRepository): PersonFindUseCase {
        return PersonFindService(personRepository)
    }

    @Bean
    fun personSearchService(personRepository: PersonRepository): PersonSearchService {
        return PersonSearchService(personRepository)
    }

    @Bean
    fun personUpdateService(personRepository: PersonRepository): PersonUpdateService {
        return PersonUpdateService(personRepository)
    }

    @Bean
    fun fixtureLoader(
        userRepository: UserRepository,
        userGroupRepository: UserGroupRepository,
        personRepository: PersonRepository,
        passwordEncoder: PasswordEncoder
    ): FixtureLoader {
        return FixtureLoader(userRepository, userGroupRepository, personRepository, passwordEncoder)
    }
}