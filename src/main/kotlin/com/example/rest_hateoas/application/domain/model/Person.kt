package com.example.rest_hateoas.application.domain.model

import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable // so we can load sample data from json
class Person(

    val key: Key,

    var name: PersonName,

    var address: PersonAddress,

    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null,

    ) : BaseModel()