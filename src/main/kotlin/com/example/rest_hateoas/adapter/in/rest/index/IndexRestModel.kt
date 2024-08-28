package com.example.rest_hateoas.adapter.`in`.rest.index

import com.example.rest_hateoas.greeting.GreetingController
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaEntity
import com.example.rest_hateoas.person.PersonController
import com.example.rest_hateoas.adapter.`in`.rest.person.PersonRestModel
import com.example.rest_hateoas.authentication.AuthenticationController
import com.example.rest_hateoas.authentication.AuthenticationRequestModel
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

class IndexRestModel(pagedResourcesAssembler: PagedResourcesAssembler<PersonJpaEntity>) : RepresentationModel<IndexRestModel?>() {

    init {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GetIndexController::class.java).index(pagedResourcesAssembler)).withSelfRel())
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthenticationController::class.java).login(
            AuthenticationRequestModel("","")
        )).withRel("login"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).greeting(null)).withRel("greeting"))
//        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).search(null, null, null)).withRel("persons-search"))
//        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).find(null)).withRel("persons-find"))
//        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).create(PersonRestModel.empty())).withRel("persons-create"))
    }


}