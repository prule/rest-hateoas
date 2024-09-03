package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.domain.model.Key

interface PersonFindUseCase {
    fun find(value: Key): Person
}