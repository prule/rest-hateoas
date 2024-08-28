package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.KeyedCrudRepository
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaEntity
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface PersonSpringDataRepository : KeyedCrudRepository<PersonJpaEntity, Long>, PagingAndSortingRepository<PersonJpaEntity?, Long?>, QuerydslPredicateExecutor<PersonJpaEntity> {
    fun findOneByKey(key: Key): Optional<PersonJpaEntity>

    override fun findAll(predicate: Predicate, pageable: Pageable): Page<PersonJpaEntity>
}
