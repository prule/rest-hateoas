package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.domain.model.PersonAddress
import com.example.rest_hateoas.application.domain.model.PersonName
import com.example.rest_hateoas.application.domain.model.Key
import java.time.LocalDate

class PersonFixtures {
    enum class Persons(val person: Person) {
        Fred(
            Person(
                Key("fred"),
                PersonName("Fred", "Flinstone", "Freddy"),
                PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.of(1990, 1, 1)
            )
        )
    }
}
