package com.example.rest_hateoas.application.service.person

import com.example.rest_hateoas.application.port.`in`.person.PersonFindUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person

class PersonFindService(val personRepository: PersonRepository): PersonFindUseCase {
    override fun find(value: Key): Person {
        return personRepository.findOneByKey(value)
    }
}