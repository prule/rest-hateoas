package com.example.rest_hateoas.person

import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

class PersonModelAssembler : RepresentationModelAssembler<Person, PersonModel> {
    override fun toModel(entity: Person): PersonModel {

        val model = PersonModel().fromEntity(entity)
        // links
        // any logic could be applied here to control the presence of these links - user based permissions, or entity states etc
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).find(entity.key.key)).withSelfRel())
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).update(entity.key.key, model)).withRel("persons-update"))
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).delete(entity.key.key)).withRel("persons-delete"))

        return model
    }

    fun toEntity(model: PersonModel, entity: Person) {
        model.toEntity(entity)
    }
}