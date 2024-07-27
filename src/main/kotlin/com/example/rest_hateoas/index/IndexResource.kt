package com.example.rest_hateoas.index

import com.example.rest_hateoas.common.Fields
import com.example.rest_hateoas.greeting.GreetingController
import com.example.rest_hateoas.person.PersonApi
import com.example.rest_hateoas.person.PersonSearchCriteria
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

class IndexResource @JsonCreator constructor() : RepresentationModel<IndexResource?>() {

    init {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IndexController::class.java).index()).withSelfRel())
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).greeting(null)).withRel("greeting"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonApi::class.java).search(PersonSearchCriteria(), Fields.all(), Pageable.ofSize(20), null)).withRel("persons-search"))
    }
}