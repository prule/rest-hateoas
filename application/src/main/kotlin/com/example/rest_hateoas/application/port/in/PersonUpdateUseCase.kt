package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.domain.model.Person

interface PersonUpdateUseCase {
    fun update(value: Person)
}