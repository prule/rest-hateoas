package com.example.rest_hateoas.adapter.out.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Embeddable
@Serializable
class PersonNameJpaEntity(

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    var otherNames: String? = null
)

