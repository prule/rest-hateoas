package com.example.rest_hateoas.application.domain.service.person

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.application.port.`in`.FindUserUseCase
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import com.example.rest_hateoas.application.port.`in`.PersonSearchUseCase
import com.example.rest_hateoas.application.port.`in`.PersonUpdateUseCase
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.common.Key
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PersonFindService(val personRepository: PersonRepository): PersonFindUseCase {
    override fun find(value: Key): Person {
        return personRepository.findOneByKey(value)
    }
}