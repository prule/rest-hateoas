package com.example.rest_hateoas.application.domain.service.person

import com.example.rest_hateoas.application.port.`in`.PersonDeleteUseCase
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaRepository
import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonDeleteService(val personRepository: PersonRepository): PersonDeleteUseCase {
    override fun delete(key: Key) {
        personRepository.delete(key)
    }
}