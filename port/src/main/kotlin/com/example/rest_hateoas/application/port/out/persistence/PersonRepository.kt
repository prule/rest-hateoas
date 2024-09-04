package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.application.port.`in`.PersonSearchCriteria
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PersonRepository {
    fun findOneByKey(key: Key): Person
    fun findIfExists(key: Key): Person?
    fun findAll(criteria: PersonSearchCriteria, pageable: Pageable): Page<Person>
    fun save(value: Person)
    fun delete(key: Key)
}