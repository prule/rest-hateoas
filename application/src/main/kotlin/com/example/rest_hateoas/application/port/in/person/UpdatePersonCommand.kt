package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.domain.model.Person
import com.example.rest_hateoas.domain.model.PersonAddress
import com.example.rest_hateoas.domain.model.PersonName
import java.time.LocalDate

class UpdatePersonCommand(
    val person: Person,
    val newValues: UpdatePersonFields
) {
    data class UpdatePersonFields(
        var name: PersonName,
        var address: PersonAddress,
        var dateOfBirth: LocalDate? = null,
        var version: Long
    )
}