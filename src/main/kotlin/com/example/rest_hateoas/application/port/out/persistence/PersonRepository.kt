package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.NotFoundException
import jakarta.persistence.EntityManagerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PersonRepository(
    val springDataRepository: PersonSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) {
    fun findOneByKey(key: Key): Person {
        val value = findIfExists(key)
        if (value == null) {
            throw NotFoundException("Person $key not found")
        } else
            return value
    }

    fun findIfExists(key: Key): Person? {
        val value = springDataRepository.findOneByKey(key)
        return if (value.isPresent) {
            PersonMapper.toDomain(value.get())
        } else {
            null
        }
    }

    fun findAll(criteria: PersonSearchCriteria, pageable: Pageable): Page<Person> {
        val page = springDataRepository.findAll(criteria.toPredicate(), pageable)
        return PageImpl(page.content.map { PersonMapper.toDomain(it!!) }, page.pageable, page.totalElements)
    }

    fun save(value: Person) {
        springDataRepository.save(PersonMapper.toJpaEntity(value))
    }

    fun delete(key: Key) {
        springDataRepository.delete(springDataRepository.findOneByKey(key).get())
    }


}