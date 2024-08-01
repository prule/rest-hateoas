package com.example.rest_hateoas.index

import com.example.rest_hateoas.common.Fields
import com.example.rest_hateoas.greeting.GreetingController
import com.example.rest_hateoas.person.Person
import com.example.rest_hateoas.person.PersonController
import com.example.rest_hateoas.person.PersonModel
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

class IndexResource @JsonCreator constructor(pagedResourcesAssembler: PagedResourcesAssembler<Person>) : RepresentationModel<IndexResource?>() {

    init {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IndexController::class.java).index(pagedResourcesAssembler)).withSelfRel())
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).greeting(null)).withRel("greeting"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).search(null,null,null, null, 0, 15, null, pagedResourcesAssembler)).withRel("persons-search"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).find("", null)).withRel("persons-find"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).create(PersonModel(), null)).withRel("persons-create"))
    }
}