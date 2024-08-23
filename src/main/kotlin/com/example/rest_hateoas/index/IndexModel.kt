package com.example.rest_hateoas.index

import com.example.rest_hateoas.greeting.GreetingController
import com.example.rest_hateoas.person.Person
import com.example.rest_hateoas.person.PersonController
import com.example.rest_hateoas.person.PersonModel
import com.example.rest_hateoas.authentication.AuthenticationController
import com.example.rest_hateoas.authentication.AuthenticationRequestModel
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

class IndexModel(pagedResourcesAssembler: PagedResourcesAssembler<Person>) : RepresentationModel<IndexModel?>() {

    init {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IndexController::class.java).index(pagedResourcesAssembler)).withSelfRel())
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthenticationController::class.java).login(
            AuthenticationRequestModel("","")
        )).withRel("login"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).greeting(null)).withRel("greeting"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).search(null, null, null)).withRel("persons-search"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).find(null)).withRel("persons-find"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController::class.java).create(PersonModel())).withRel("persons-create"))
    }


}