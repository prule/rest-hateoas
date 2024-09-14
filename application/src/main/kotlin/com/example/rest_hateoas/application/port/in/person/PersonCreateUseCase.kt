package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.domain.model.Person

interface PersonCreateUseCase {
    fun create(value: Person)
}