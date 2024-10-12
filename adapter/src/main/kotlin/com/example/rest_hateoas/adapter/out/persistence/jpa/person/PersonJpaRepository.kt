package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.NotFoundException
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.PageJpaMapper
import com.example.rest_hateoas.application.port.`in`.person.PersonFilter
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

    override fun findByKey(key: Key): Person? {
        val result = springDataRepository.findByKey(KeyJpaMapper.toJpaEntity(key))
        return result?.let { PersonJpaMapper.toDomain(it) }
    }

    override fun findAll(filter: PersonFilter, page: Page): PageData<Person> {
        val pageable = springDataRepository.findAll(
            PersonSearchCriteriaQueryDsl(
                filter.filter,
                filter.from,
                filter.to
            ).toPredicate(), PageJpaMapper.toJpaEntity(page)
        )
        return PageData(
            pageable.content.map { PersonJpaMapper.toDomain(it!!) },
            pageable.totalElements,
            Page(
                pageable.pageable.pageNumber,
                pageable.pageable.pageSize,
                Sort(
                    pageable.pageable.sort.map { Order(it.property, it.direction.name) }.toList()
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