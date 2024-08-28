package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonAddressJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonNameJpaEntity
import com.example.rest_hateoas.application.domain.model.PersonAddress
import com.example.rest_hateoas.application.domain.model.PersonName

class PersonAddressMapper {
    companion object {
        fun toDomain(value: PersonAddressJpaEntity): PersonAddress {
            return PersonAddress(
                value.line1,
                value.line2,
                value.city,
                value.state,
                value.country,
                value.postcode
            )
        }

        fun toJpaEntity(value: PersonAddress): PersonAddressJpaEntity {
            return PersonAddressJpaEntity(
                value.line1,
                value.line2,
                value.city,
                value.state,
                value.country,
                value.postcode
            )
        }
    }
}