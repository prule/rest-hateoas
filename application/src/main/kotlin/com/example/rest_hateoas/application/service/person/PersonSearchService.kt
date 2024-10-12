package com.example.rest_hateoas.application.service.person

import com.example.rest_hateoas.application.port.`in`.person.PersonFilter
import com.example.rest_hateoas.application.port.`in`.person.PersonSearchUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Person

class PersonSearchService(val personRepository: PersonRepository): PersonSearchUseCase {
    override fun search(criteria: PersonFilter): PageData<Person> {
        return personRepository.findAll(criteria, criteria.page)
    }
}