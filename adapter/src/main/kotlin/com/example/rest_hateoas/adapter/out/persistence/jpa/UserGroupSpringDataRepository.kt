package com.example.rest_hateoas.adapter.out.persistence.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserGroupSpringDataRepository : KeyedCrudRepository<UserGroupJpaEntity?, Long?> {
    fun findAll(pageable: Pageable): Page<UserGroupJpaEntity>
}
