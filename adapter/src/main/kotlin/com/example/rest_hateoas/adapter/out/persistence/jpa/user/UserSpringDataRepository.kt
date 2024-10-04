package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyedCrudRepository
import com.example.rest_hateoas.adapter.out.persistence.jpa.person.PersonJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserSpringDataRepository : KeyedCrudRepository<UserJpaEntity?, Long?> {
    fun findAll(pageable: Pageable): Page<UserJpaEntity>

    fun findByUsername(username: String): UserJpaEntity?

    fun deleteByKey(key: KeyJpaEntity)

    fun findOneByKey(key: KeyJpaEntity): Optional<UserJpaEntity>

}
