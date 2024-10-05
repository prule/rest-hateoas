package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.port.`in`.person.PersonSearchCriteria
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person

interface PersonRepository {
    fun findOneByKey(key: Key): Person
    fun findIfExists(key: Key): Person?
    fun findAll(criteria: PersonSearchCriteria): PageData<Person>
    fun save(value: Person)
    fun delete(key: Key)
}