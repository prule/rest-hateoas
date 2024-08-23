package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.Key
import java.time.LocalDate

class PersonFixtures {
    enum class Persons(val person: Person) {
        Fred(
            Person(
                Key("fred"),
                PersonName("Fred", "Flinstone", "Freddy"),
                Address("line1", "line2", "city", "state", "country", "postcode"),
                LocalDate.now()
            )
        )
    }
}
