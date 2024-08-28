package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaEntity
import com.example.rest_hateoas.application.domain.model.Person

class PersonMapper {
    companion object {
        fun toDomain(value: PersonJpaEntity): Person {
            val domain = Person(
                value.key,
                PersonNameMapper.toDomain(value.name),
                PersonAddressMapper.toDomain(value.address),
                value.dateOfBirth,
            )
//            domain.createdBy = UserMapper.toDomain(value.createdBy)
//            domain.lastModifiedBy = UserMapper.toDomain(value.lastModifiedBy)
            domain.createdBy = value.createdBy
            domain.createdDate = value.createdDate
            domain.lastModifiedDate = value.lastModifiedDate
            domain.id = value.id
            domain.version = value.version
            return domain
        }

        // need to consider what fields can be set by what type of user
        fun toJpaEntity(value: Person): PersonJpaEntity {
            val person = PersonJpaEntity(
                value.key,
                PersonNameMapper.toJpaEntity(value.name),
                PersonAddressMapper.toJpaEntity(value.address),
                value.dateOfBirth,
                value.id
            )
            person.version = value.version

            // modified date and modified by are managed by spring data
            return person
        }
    }
}