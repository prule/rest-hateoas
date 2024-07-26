package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.KeyedCrudRepository
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface PersonRepository : KeyedCrudRepository<Person, Long>, PagingAndSortingRepository<Person?, Long?>, QuerydslPredicateExecutor<Person> {
    fun findOneByKey(key: Key?): Optional<Person>

    override fun findAll(predicate: Predicate, pageable: Pageable): Page<Person>
}
