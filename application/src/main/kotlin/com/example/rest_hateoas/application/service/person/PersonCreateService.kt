package com.example.rest_hateoas.application.service.person

import com.example.rest_hateoas.application.port.`in`.person.PersonCreateUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.model.Person
import org.springframework.stereotype.Service

@Service
class PersonCreateService(val personRepository: PersonRepository): PersonCreateUseCase {
    override fun create(value: Person) {
        personRepository.save(value)
    }
}