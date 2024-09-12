package com.example.rest_hateoas.adapter.`in`.rest.index

import com.example.rest_hateoas.adapter.`in`.rest.person.*
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaEntity
import com.example.rest_hateoas.adapter.`in`.rest.support.authentication.AuthenticationRequestModel
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import com.example.rest_hateoas.adapter.`in`.rest.support.authentication.AuthenticationController

class IndexRestModel() : RepresentationModel<IndexRestModel?>() {

    init {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GetIndexController::class.java).index()).withSelfRel())
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthenticationController::class.java).login(
            AuthenticationRequestModel("","")
        )).withRel("login"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonSearchController::class.java).search(null, null, null)).withRel("persons-search"))
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonFindController::class.java).find()).withRel("persons-find")).
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonCreateController::class.java).create(
            PersonCreateRestModel.empty())).withRel("persons-create"))
    }


}