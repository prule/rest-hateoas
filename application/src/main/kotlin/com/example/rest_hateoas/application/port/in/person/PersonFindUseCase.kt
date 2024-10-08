package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person

interface PersonFindUseCase {
    fun find(value: Key): Person
}