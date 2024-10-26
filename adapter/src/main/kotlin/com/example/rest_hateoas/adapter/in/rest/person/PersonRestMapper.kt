package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.person.PersonNameMapper
import com.example.rest_hateoas.application.port.`in`.person.UpdatePersonCommand
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person
import com.example.rest_hateoas.domain.model.ModelMetadata
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service

@Service
class PersonRestMapper {

    // tag::PersonRestMapper_fromDomain[]
    fun fromDomain(value: Person): PersonRestModel {
        val model = PersonRestModel(
            value.metaData.version,
            value.key.key,
            PersonNameRestModel.fromDomain(value.name),
            PersonAddressRestModel.fromDomain(value.address),
            value.dateOfBirth,
            ModelMetadataRestMapper.fromDomain(value.metaData)
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
                    WebMvcLinkBuilder.methodOn(PersonUpdateController::class.java).update(value.key.key, PersonUpdateRestModel.empty())
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
    // end::PersonRestMapper_fromDomain[]

    fun toNewDomain(value: PersonCreateRestModel): Person {

        return Person(
            Key(),
            PersonNameRestModel.toDomain(value.name),
            PersonAddressRestModel.toDomain(value.address),
            value.dateOfBirth,
        )

    }

    fun toDomain(key: String, value: PersonUpdateRestModel): Person {

        return Person(
            Key(key),
            PersonNameRestModel.toDomain(value.name),
            PersonAddressRestModel.toDomain(value.address),
            value.dateOfBirth,
            ModelMetadata(value.version)
        )

    }

    fun toUpdateCommand(person: Person, model: PersonUpdateRestModel): UpdatePersonCommand {
        return UpdatePersonCommand(
            person,
            UpdatePersonCommand.UpdatePersonFields(
                PersonNameRestModel.toDomain(model.name),
                PersonAddressRestModel.toDomain(model.address),
                model.dateOfBirth,
                model.version
            )
        )
    }


}