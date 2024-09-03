package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.application.domain.model.UserGroup

class UserGroupMapper {

    companion object {
        fun toDomain(value: UserGroupJpaEntity): UserGroup {
            return UserGroup(
                value.id,
                KeyMapper.toDomain(value.key),
                value.name,
                value.description,
                value.enabled
            )
        }

        fun toJpaEntity(value: UserGroup): UserGroupJpaEntity {
            return UserGroupJpaEntity(
                KeyMapper.toJpaEntity(value.key),
                value.name,
                value.description,
                value.enabled,
                value.id
            )
        }
    }

}