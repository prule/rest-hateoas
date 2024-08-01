package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Fields
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

class PersonModelAssembler(private val fields: Fields) : RepresentationModelAssembler<Person, PersonModel> {
    override fun toModel(entity: Person): PersonModel {
        val model = PersonModel().fromEntity(entity, fields)
        // links
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).find(entity.key.key, fields.toString())).withSelfRel())
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).update(entity.key.key, fields.toString(), model)).withRel("persons-update"))
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).delete(entity.key.key)).withRel("persons-delete"))

        return model
    }

    fun toEntity(model: PersonModel, entity: Person) {
        model.toEntity(entity, fields)
    }
}