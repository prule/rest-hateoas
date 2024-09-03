package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.common.KeyedCrudRepository
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface PersonSpringDataRepository : KeyedCrudRepository<PersonJpaEntity, Long>, PagingAndSortingRepository<PersonJpaEntity?, Long?>, QuerydslPredicateExecutor<PersonJpaEntity> {
    fun findOneByKey(key: KeyJpaEntity): Optional<PersonJpaEntity>

    override fun findAll(predicate: Predicate, pageable: Pageable): Page<PersonJpaEntity>
}
