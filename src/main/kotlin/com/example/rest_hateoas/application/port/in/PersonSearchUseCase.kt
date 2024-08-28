package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.application.domain.model.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PersonSearchUseCase {
    fun search(criteria: PersonSearchCriteria, pageable: Pageable): Page<Person>
}