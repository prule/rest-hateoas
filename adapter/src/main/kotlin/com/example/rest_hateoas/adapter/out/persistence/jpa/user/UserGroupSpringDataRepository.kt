package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyedCrudRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserGroupSpringDataRepository : KeyedCrudRepository<UserGroupJpaEntity?, Long?> {
    fun findAll(pageable: Pageable): Page<UserGroupJpaEntity>
    fun deleteByKey(key: KeyJpaEntity)

    fun findOneByKey(key: KeyJpaEntity): UserGroupJpaEntity?
}
