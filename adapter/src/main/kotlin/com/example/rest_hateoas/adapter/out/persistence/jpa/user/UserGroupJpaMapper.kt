package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.domain.model.UserGroup

class UserGroupJpaMapper {

    companion object {
        fun toDomain(value: UserGroupJpaEntity): UserGroup {
            return UserGroup(
                KeyJpaMapper.toDomain(value.key),
                value.name,
                value.description,
                value.enabled
            )
        }

        fun toEntity(value: UserGroup): UserGroupJpaEntity {
            return UserGroupJpaEntity(
                KeyJpaMapper.toJpaEntity(value.key),
                value.name,
                value.description,
                value.enabled,
            )
        }
    }

}