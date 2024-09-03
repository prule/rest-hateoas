package com.example.rest_hateoas.application.domain.service.person

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.`in`.PersonSearchUseCase
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaRepository
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PersonSearchService(val personRepository: PersonRepository): PersonSearchUseCase {
    override fun search(criteria: PersonSearchCriteria, pageable: Pageable): Page<Person> {
        return personRepository.findAll(criteria, pageable)
    }
}