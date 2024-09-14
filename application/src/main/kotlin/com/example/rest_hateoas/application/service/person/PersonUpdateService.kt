package com.example.rest_hateoas.application.service.person

import com.example.rest_hateoas.application.port.`in`.person.PersonUpdateUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.model.Person
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PersonUpdateService(val personRepository: PersonRepository): PersonUpdateUseCase {
    override fun update(value: Person) {
        personRepository.save(value)
    }
}