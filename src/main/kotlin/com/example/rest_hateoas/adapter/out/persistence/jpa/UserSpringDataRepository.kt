package com.example.rest_hateoas.adapter.out.persistence.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserSpringDataRepository : KeyedCrudRepository<UserJpaEntity?, Long?> {
    fun findAll(pageable: Pageable): Page<UserJpaEntity>

    fun findByUsername(username: String): UserJpaEntity?
}
