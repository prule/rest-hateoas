package com.example.rest_hateoas

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class Greeting @JsonCreator constructor(
    @param:JsonProperty(
        "content"
    ) val content: String
) : RepresentationModel<Greeting?>()