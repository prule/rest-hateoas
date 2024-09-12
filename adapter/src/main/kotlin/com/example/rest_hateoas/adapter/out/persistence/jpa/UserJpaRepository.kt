package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import jakarta.persistence.EntityManagerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class UserJpaRepository(
    val springDataRepository: UserSpringDataRepository,
    val userGroupSpringDataRepository: UserGroupSpringDataRepository,
    val entityManagerFactory: EntityManagerFactory
) : UserRepository {

    override fun findAll(pageable: Pageable): Page<User> {
        val result = springDataRepository.findAll(pageable)
        return PageImpl(result.content.map { UserMapper.toDomain(it!!) }, result.pageable, result.totalElements)
    }

    override fun findByUsername(username: String): User? {
        val result = springDataRepository.findByUsername(username)
        return result?.let { UserMapper.toDomain(it) }
    }

    override fun findByKey(key: Key): User? {
        val result = springDataRepository.findByKey(KeyMapper.toJpaEntity(key))
        return result?.let { UserMapper.toDomain(it) }
    }

    override fun save(user: User) {
        entityManagerFactory.createEntityManager().use { entityManager ->
            entityManager.getTransaction().begin()
            entityManager.merge(UserMapper.toJpaEntity(user, userGroupSpringDataRepository))
            entityManager.getTransaction().commit()
        }
    }

}