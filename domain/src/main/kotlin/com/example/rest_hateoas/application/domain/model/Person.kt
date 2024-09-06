package com.example.rest_hateoas.application.domain.model

import java.time.LocalDate

class Person(

    val key: Key,

    var name: PersonName,

    var address: PersonAddress,

    var dateOfBirth: LocalDate? = null,

    ) : BaseModel()