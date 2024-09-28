package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyedCrudRepository
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface PersonSpringDataRepository : KeyedCrudRepository<PersonJpaEntity, Long>, PagingAndSortingRepository<PersonJpaEntity?, Long?>, QuerydslPredicateExecutor<PersonJpaEntity> {
    fun findOneByKey(key: KeyJpaEntity): Optional<PersonJpaEntity>

    fun deleteByKey(key: KeyJpaEntity)

    override fun findAll(predicate: Predicate, pageable: Pageable): Page<PersonJpaEntity>

}
