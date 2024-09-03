package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.application.domain.model.UserGroup
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import jakarta.persistence.EntityManagerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


@Repository
class UserGroupJpaRepository(
    val springDataRepository: UserGroupSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) : UserGroupRepository {
    override fun findAll(pageable: Pageable): Page<UserGroup> {
        val result = springDataRepository.findAll(pageable)
        return PageImpl(result.content.map { UserGroupMapper.toDomain(it!!) }, result.pageable, result.totalElements)
    }

    override fun save(userGroup: UserGroup) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(UserGroupMapper.toJpaEntity(userGroup))
            entityManager.getTransaction().commit()
        }
    }
}