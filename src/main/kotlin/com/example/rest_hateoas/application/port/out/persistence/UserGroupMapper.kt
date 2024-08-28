package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserGroupJpaEntity
import com.example.rest_hateoas.application.domain.model.UserGroup

class UserGroupMapper {

    companion object {
        fun toDomain(value: UserGroupJpaEntity): UserGroup {
            return UserGroup(
                value.id,
                value.key,
                value.name,
                value.description,
                value.enabled
            )
        }

        fun toJpaEntity(value: UserGroup): UserGroupJpaEntity {
            return UserGroupJpaEntity(
                value.key,
                value.name,
                value.description,
                value.enabled,
                value.id
            )
        }
    }

}