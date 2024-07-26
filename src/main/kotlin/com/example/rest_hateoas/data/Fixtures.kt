package com.example.rest_hateoas.data

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.person.Person
import com.example.rest_hateoas.person.PersonName
import java.time.LocalDate

class Fixtures {
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
