package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person
import com.example.rest_hateoas.domain.model.PersonAddress
import com.example.rest_hateoas.domain.model.PersonName
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
        ),
        Sortable1(
            Person(
                Key("sortable1"),
                PersonName("A", "A", "sortable"),
                PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.of(1990, 1, 1)
            )
        ),
        Sortable2(
            Person(
                Key("sortable2"),
                PersonName("A", "B", "sortable"),
                PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.of(1990, 1, 1)
            )
        ),
        Sortable3(
            Person(
                Key("sortable3"),
                PersonName("B", "A", "sortable"),
                PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.of(1990, 1, 1)
            )
        ),
        Sortable4(
            Person(
                Key("sortable4"),
                PersonName("B", "B", "sortable"),
                PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.of(1990, 1, 1)
            )
        )

    }
}
