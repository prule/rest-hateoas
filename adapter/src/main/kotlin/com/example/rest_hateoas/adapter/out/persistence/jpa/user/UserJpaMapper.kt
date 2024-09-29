package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.domain.model.User

class UserJpaMapper {

    companion object {
        fun toDomain(value: UserJpaEntity?): User? {
            if (value == null) {
                return null
            }
            return User(
                KeyJpaMapper.toDomain(value.key),
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { UserGroupMapper.toDomain(it) },
                value.id
            )
        }

        fun toJpaEntity(value: User, userGroupSpringDataRepository: UserGroupSpringDataRepository): UserJpaEntity {
            return UserJpaEntity(
                KeyJpaMapper.toJpaEntity(value.key),
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { userGroupSpringDataRepository.findByKey(KeyJpaMapper.toJpaEntity(it.key))!! },
                value.id
            )
        }
    }

}