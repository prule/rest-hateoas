package com.example.rest_hateoas.application.service.person

import com.example.rest_hateoas.application.port.`in`.PersonDeleteUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.model.Key
import org.springframework.stereotype.Service

@Service
class PersonDeleteService(val personRepository: PersonRepository): PersonDeleteUseCase {
    override fun delete(key: Key) {
        personRepository.delete(key)
    }
}