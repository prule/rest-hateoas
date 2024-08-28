package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.UserGroupJpaEntity
import com.example.rest_hateoas.common.KeyedCrudRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserGroupSpringDataRepository : KeyedCrudRepository<UserGroupJpaEntity?, Long?> {
    fun findAll(pageable: Pageable): Page<UserGroupJpaEntity>
}
