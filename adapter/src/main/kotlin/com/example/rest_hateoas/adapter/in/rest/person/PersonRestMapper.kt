package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service

@Service
class PersonRestMapper(
    private val personFindUseCase: PersonFindUseCase
) {

    fun fromDomain(value: Person): PersonRestModel {
        val model = PersonRestModel(
            value.version ?: 0,
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
        model.addIf(
            hasPermission("persons-update"), {
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonUpdateController::class.java).update(value.key.key, model)
                ).withRel("persons-update")
            }
        )
        model.addIf(
            hasPermission("persons-delete"), {
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonDeleteController::class.java).delete(value.key.key)
                ).withRel("persons-delete")
            }
        )
        return model
    }

    private fun hasPermission(rel: String): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    fun toNewDomain(value: PersonCreateRestModel): Person {

        return Person(
            Key(),
            PersonNameRestModel.toDomain(value.name),
            PersonAddressRestModel.toDomain(value.address),
            value.dateOfBirth
        )

    }

    fun toExistingDomain(key: String, value: PersonRestModel): Person {

        val name = PersonNameRestModel.toDomain(value.name)
        val address = PersonAddressRestModel.toDomain(value.address)

        // load the current version of the entity from the database so we have all the fields populated appropriately
        // (not all fields are exposed in the rest model, so we need to load the full entity from the database)
        // (not all fields are updateable by the user eg key, id, created/modified etc)

        val person = personFindUseCase.find(Key(key))

        // update the fields of the entity with the values from the rest model
        person.name = name
        person.address = address
        person.dateOfBirth = value.dateOfBirth

        // set the version to that version the user was working with - so that conflicts can be detected
        // todo how would we protect version from being manipulated by the user?
        person.version = value.version

        return person
    }


}