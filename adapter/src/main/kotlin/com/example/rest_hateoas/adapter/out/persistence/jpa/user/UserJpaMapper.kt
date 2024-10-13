package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.ModelMetadataJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.ModelMetadataJpaMapper
import com.example.rest_hateoas.domain.model.User

class UserJpaMapper {

    companion object {
        fun toDomain(value: UserJpaEntity): User {
            return User(
                KeyJpaMapper.toDomain(value.key),
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { UserGroupJpaMapper.toDomain(it) },
                ModelMetadataJpaMapper.toDomain(value.metadata, value.version)
            )
        }

        fun toJpaEntity(
            value: User,
            existing: UserJpaEntity?,
            userGroupSpringDataRepository: UserGroupSpringDataRepository
        ): UserJpaEntity {
            return UserJpaEntity(
                KeyJpaMapper.toJpaEntity(value.key),
                value.username,
                value.password,
                value.firstName,
                value.lastName,
                value.enabled,
                value.groups.map { userGroupSpringDataRepository.findByKey(KeyJpaMapper.toJpaEntity(it.key))!! },
                existing?.id,
                value.metaData.version,
                existing?.metadata ?: ModelMetadataJpaEntity.newInstance()

            )
        }
    }

}