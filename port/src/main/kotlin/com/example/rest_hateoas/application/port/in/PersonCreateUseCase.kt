package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.application.domain.model.Person

interface PersonCreateUseCase {
    fun create(value: Person)
}