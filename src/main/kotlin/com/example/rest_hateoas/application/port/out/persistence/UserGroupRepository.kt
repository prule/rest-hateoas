package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.domain.model.UserGroup
import jakarta.persistence.EntityManagerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


@Repository
class UserGroupRepository(
    val springDataRepository: UserGroupSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) {
    fun findAll(pageable: Pageable): Page<UserGroup> {
        val result = springDataRepository.findAll(pageable)
        return PageImpl(result.content.map { UserGroupMapper.toDomain(it!!) }, result.pageable, result.totalElements)
    }

    fun save(userGroup: UserGroup) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(UserGroupMapper.toJpaEntity(userGroup))
            entityManager.getTransaction().commit()
        }
    }
}