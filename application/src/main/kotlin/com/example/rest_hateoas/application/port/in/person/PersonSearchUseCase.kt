package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.domain.model.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PersonSearchUseCase {
    fun search(criteria: PersonSearchCriteria, pageable: Pageable): Page<Person>
}