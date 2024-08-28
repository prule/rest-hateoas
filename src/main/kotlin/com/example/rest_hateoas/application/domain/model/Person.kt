package com.example.rest_hateoas.application.domain.model

import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.ZonedDateTime

@Serializable // so we can load sample data from json
class Person(

    val key: Key,

    var name: PersonName,

    var address: PersonAddress,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null,

) : BaseModel()