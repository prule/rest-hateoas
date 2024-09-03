package com.example.rest_hateoas.application.domain.service.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.`in`.PersonUpdateUseCase
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaRepository
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PersonUpdateService(val personRepository: PersonRepository): PersonUpdateUseCase {
    override fun update(value: Person) {
        personRepository.save(value)
    }
}