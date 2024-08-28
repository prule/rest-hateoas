package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.common.Key
import jakarta.persistence.EntityManagerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    val springDataRepository: UserSpringDataRepository,
    val userGroupSpringDataRepository: UserGroupSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) {

    fun findAll(pageable: Pageable): Page<User> {
        val result = springDataRepository.findAll(pageable)
        return PageImpl(result.content.map { UserMapper.toDomain(it!!) }, result.pageable, result.totalElements)
    }

    fun findByUsername(username: String): User? {
        val result = springDataRepository.findByUsername(username)
        return result?.let { UserMapper.toDomain(it) }
    }

    fun findByKey(key: Key): User? {
        val result = springDataRepository.findByKey(key)
        return result?.let { UserMapper.toDomain(it) }
    }

    fun save(user: User) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(UserMapper.toJpaEntity(user, userGroupSpringDataRepository))
            entityManager.getTransaction().commit()
        }
    }

}