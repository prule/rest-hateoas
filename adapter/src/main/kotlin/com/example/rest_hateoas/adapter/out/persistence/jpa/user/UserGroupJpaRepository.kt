package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.`in`.rest.support.http.NotFoundException
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.PageJpaMapper
import com.example.rest_hateoas.domain.model.UserGroup
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.domain.Order
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.Sort
import com.example.rest_hateoas.domain.model.Key
import jakarta.persistence.EntityManagerFactory
import org.springframework.stereotype.Repository


@Repository
class UserGroupJpaRepository(
    val springDataRepository: UserGroupSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) : UserGroupRepository {
    override fun findOneByKey(key: Key): UserGroup {
        val value = findIfExists(key)
        if (value == null) {
            throw NotFoundException("$key not found")
        } else
            return value
    }

    override fun findIfExists(key: Key): UserGroup? {
        val value = springDataRepository.findOneByKey(KeyJpaMapper.toJpaEntity(key))
        return if (value!= null) {
            UserGroupJpaMapper.toDomain(value)
        } else {
            null
        }
    }

    override fun findByKey(key: Key): UserGroup? {
        val result = springDataRepository.findByKey(KeyJpaMapper.toJpaEntity(key))
        return result?.let { UserGroupJpaMapper.toDomain(it) }
    }

    override fun findAll(pageable: Page): PageData<UserGroup> {
        val page = springDataRepository.findAll(PageJpaMapper.toJpaEntity(pageable))
        return PageData(
            page.content.map { UserGroupJpaMapper.toDomain(it!!) },
            page.totalElements,
            com.example.rest_hateoas.domain.Page(
                page.pageable.pageNumber,
                page.pageable.pageSize,
                Sort(
                    page.pageable.sort.map { Order(it.property, it.direction.name) }.toList()
                )
            )
        )
    }

    override fun save(userGroup: UserGroup) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(UserGroupJpaMapper.toJpaEntity(userGroup))
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