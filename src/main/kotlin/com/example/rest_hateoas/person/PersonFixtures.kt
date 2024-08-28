package com.example.rest_hateoas.person

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonNameRestModel
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaEntity
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.domain.model.PersonAddress
import com.example.rest_hateoas.application.domain.model.PersonName
import com.example.rest_hateoas.common.Key
import java.time.LocalDate

class PersonFixtures {
    enum class Persons(val person: Person) {
        Fred(
            Person(
                Key("fred"),
                PersonName("Fred", "Flinstone", "Freddy"),
                PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.now()
            )
        )
    }
}
