package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.domain.model.PersonName

class PersonNameMapper {
    companion object {
        fun toDomain(value: PersonNameJpaEntity): PersonName {
            return PersonName(
                value.firstName,
                value.lastName,
                value.otherNames,
            )
        }

        fun toJpaEntity(value: PersonName): PersonNameJpaEntity {
            return PersonNameJpaEntity(
                value.firstName,
                value.lastName,
                value.otherNames
            )
        }
    }
}