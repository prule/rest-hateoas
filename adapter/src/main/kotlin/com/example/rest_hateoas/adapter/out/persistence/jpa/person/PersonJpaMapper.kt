package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.ModelMetadataJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.ModelMetadataJpaMapper
import com.example.rest_hateoas.domain.model.ModelMetadata
import com.example.rest_hateoas.domain.model.Person

class PersonJpaMapper {
    companion object {
        fun toDomain(value: PersonJpaEntity): Person {
            return Person(
                KeyJpaMapper.toDomain(value.key),
                PersonNameMapper.toDomain(value.name),
                PersonAddressMapper.toDomain(value.address),
                value.dateOfBirth,
                ModelMetadataJpaMapper.toDomain(value.metadata, value.version)
            )
        }

        fun toJpaEntity(value: Person, existingPerson: PersonJpaEntity?): PersonJpaEntity {

            return PersonJpaEntity(
                KeyJpaMapper.toJpaEntity(value.key),
                PersonNameMapper.toJpaEntity(value.name),
                PersonAddressMapper.toJpaEntity(value.address),
                value.dateOfBirth,
                existingPerson?.id,
                value.metaData.version,
                existingPerson?.metadata ?: ModelMetadataJpaEntity.newInstance()
            )

            // modified date and modified by are managed by spring data
        }
    }
}