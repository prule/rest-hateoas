package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.common.Key
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PersonDeleteUseCase {
    fun delete(key: Key)
}