package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.domain.model.PersonAddress

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