package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.domain.model.User

class UserMapper {

    companion object {
        fun toDomain(value: UserJpaEntity?): User? {
            if (value == null) {
                return null
            }
            return User(
                KeyMapper.toDomain(value.key),
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
                KeyMapper.toJpaEntity(value.key),
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { userGroupSpringDataRepository.findByKey(KeyMapper.toJpaEntity(it.key))!! },
                value.id
            )
        }
    }

}