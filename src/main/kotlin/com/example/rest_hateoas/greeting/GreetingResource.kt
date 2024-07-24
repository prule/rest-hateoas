package com.example.rest_hateoas.greeting

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class GreetingResource @JsonCreator constructor(
    @param:JsonProperty("content") val content: String
) : RepresentationModel<GreetingResource?>()