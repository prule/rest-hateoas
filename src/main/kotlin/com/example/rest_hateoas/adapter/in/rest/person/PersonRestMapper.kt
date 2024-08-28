package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import com.example.rest_hateoas.common.Key
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service

@Service
class PersonRestMapper(
    private val personFindUseCase: PersonFindUseCase
) {

    fun fromDomain(value: Person): PersonRestModel {
        val model = PersonRestModel(
            value.version,
            value.id,
            value.key.key,
            PersonNameRestModel.fromDomain(value.name),
            PersonAddressRestModel.fromDomain(value.address),
            value.dateOfBirth
        )

        // links
        // any logic could be applied here to control the presence of these links - user based permissions, or entity states etc
        model.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonFindController::class.java).find(value.key.key)
            ).withSelfRel()
        )
        model.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonUpdateController::class.java).update(value.key.key, model)
            ).withRel("persons-update")
        )
//        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).delete(value.key.key)).withRel("persons-delete"))
        return model
    }

    fun toDomain(value: PersonRestModel): Person {

        val person = Person(
            if (value.key != null) {
                Key(value.key!!)
            } else {
                Key()
            },
            PersonNameRestModel.toDomain(value.name),
            PersonAddressRestModel.toDomain(value.address),
            value.dateOfBirth,
        )
        person.id = value.id
        person.version = value.version

        return person
    }

}