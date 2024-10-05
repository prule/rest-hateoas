package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.NotFoundException
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.PageJpaMapper
import com.example.rest_hateoas.application.port.`in`.person.PersonSearchCriteria
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.Order
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.Sort
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person
import jakarta.persistence.EntityManagerFactory
import org.springframework.stereotype.Repository

@Repository
class PersonJpaRepository(
    val springDataRepository: PersonSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) : PersonRepository {
    override fun findOneByKey(key: Key): Person {
        val value = findIfExists(key)
        if (value == null) {
            throw NotFoundException("Person $key not found")
        } else
            return value
    }

    override fun findIfExists(key: Key): Person? {
        val value = springDataRepository.findOneByKey(KeyJpaMapper.toJpaEntity(key))
        return if (value.isPresent) {
            PersonJpaMapper.toDomain(value.get())
        } else {
            null
        }
    }

    override fun findAll(criteria: PersonSearchCriteria): PageData<Person> {
        val page = springDataRepository.findAll(
            PersonSearchCriteriaQueryDsl(
                criteria.filter,
                criteria.from,
                criteria.to
            ).toPredicate(), PageJpaMapper.toJpaEntity(criteria.page)
        )
        return PageData(
            page.content.map { PersonJpaMapper.toDomain(it!!) },
            page.totalElements,
            Page(
                page.pageable.pageNumber,
                page.pageable.pageSize,
                Sort(
                    page.pageable.sort.map { Order(it.property, it.direction.name) }.toList()
                )
            )
        )
    }

    override fun save(value: Person) {
        springDataRepository.save(
            PersonJpaMapper.toJpaEntity(
                value,
                springDataRepository.findByKey(KeyJpaMapper.toJpaEntity(value.key))
            )
        )
    }


    override fun delete(key: Key) {
        springDataRepository.deleteByKey(
            KeyJpaMapper.toJpaEntity(
                // invoke findOneByKey to ensure that the key exists - if not, throw NotFoundException
                findOneByKey(key).key
            )
        )
    }


}