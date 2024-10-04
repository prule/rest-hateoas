package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.`in`.rest.support.http.NotFoundException
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.PageJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.person.PersonJpaMapper
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.domain.Order
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.Sort
import com.example.rest_hateoas.domain.model.Person
import jakarta.persistence.EntityManagerFactory
import org.springframework.stereotype.Repository

@Repository
class UserJpaRepository(
    val springDataRepository: UserSpringDataRepository,
    val userGroupSpringDataRepository: UserGroupSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) : UserRepository {

    override fun findAll(pageable: Page): PageData<User> {
        val page = springDataRepository.findAll(PageJpaMapper.toJpaEntity(pageable))
        return PageData(
            page.content.map { UserJpaMapper.toDomain(it!!) },
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

    override fun findByUsername(username: String): User? {
        val result = springDataRepository.findByUsername(username)
        return result?.let { UserJpaMapper.toDomain(it) }
    }

    override fun findOneByKey(key: Key): User {
        val value = findIfExists(key)
        if (value == null) {
            throw NotFoundException("$key not found")
        } else
            return value
    }

    override fun findIfExists(key: Key): User? {
        val value = springDataRepository.findOneByKey(KeyJpaMapper.toJpaEntity(key))
        return if (value.isPresent) {
            UserJpaMapper.toDomain(value.get())
        } else {
            null
        }
    }

    override fun findByKey(key: Key): User? {
        val result = springDataRepository.findByKey(KeyJpaMapper.toJpaEntity(key))
        return result?.let { UserJpaMapper.toDomain(it) }
    }

    override fun save(user: User) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(UserJpaMapper.toJpaEntity(user, userGroupSpringDataRepository))
            entityManager.getTransaction().commit()
        }
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