package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Person

interface PersonSearchUseCase {
    fun search(criteria: PersonSearchCriteria): PageData<Person>
}