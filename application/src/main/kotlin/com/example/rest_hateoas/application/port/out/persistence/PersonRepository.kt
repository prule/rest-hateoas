package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.port.`in`.person.PersonFilter
import com.example.rest_hateoas.domain.model.Person

interface PersonRepository: Repository<Person, PersonFilter> {


}