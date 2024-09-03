package com.example.rest_hateoas.application.domain.service.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaRepository
import com.example.rest_hateoas.application.domain.model.Key
import org.springframework.stereotype.Service

@Service
class PersonFindService(val personRepository: PersonJpaRepository): PersonFindUseCase {
    override fun find(value: Key): Person {
        return personRepository.findOneByKey(value)
    }
}