package com.example.rest_hateoas.application.domain.service.person

import com.example.rest_hateoas.application.port.`in`.PersonDeleteUseCase
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaRepository
import com.example.rest_hateoas.application.domain.model.Key
import org.springframework.stereotype.Service

@Service
class PersonDeleteService(val personRepository: PersonJpaRepository): PersonDeleteUseCase {
    override fun delete(key: Key) {
        personRepository.delete(key)
    }
}