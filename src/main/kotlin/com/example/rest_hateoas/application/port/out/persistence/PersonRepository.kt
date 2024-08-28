package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.common.Key
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
        return PersonMapper.toDomain(springDataRepository.findOneByKey(key).get())
    }

    fun findIfExists(key: Key): Person? {
        val value = springDataRepository.findOneByKey(key)
        if (value.isPresent) {
            return PersonMapper.toDomain(value.get())
        } else {
            return null
        }
    }

    fun findAll(criteria: PersonSearchCriteria, pageable: Pageable): Page<Person> {
        val page = springDataRepository.findAll(criteria.toPredicate(), pageable)
        return PageImpl(page.content.map { PersonMapper.toDomain(it!!) }, page.pageable, page.totalElements)
    }

    fun save(value: Person) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(PersonMapper.toJpaEntity(value))
            entityManager.getTransaction().commit()
        }
    }


}