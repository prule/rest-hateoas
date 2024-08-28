package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaEntity
import com.example.rest_hateoas.application.domain.model.User

class UserMapper {

    companion object {
        fun toDomain(value: UserJpaEntity?): User? {
            if (value == null) {
                return null
            }
            return User(
                value.key,
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { UserGroupMapper.toDomain(it) },
                value.id!!
            )
        }

        fun toJpaEntity(value: User, userGroupSpringDataRepository: UserGroupSpringDataRepository): UserJpaEntity {
            return UserJpaEntity(
                value.key,
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { userGroupSpringDataRepository.findByKey(it.key)!! },
                value.id
            )
        }
    }

}