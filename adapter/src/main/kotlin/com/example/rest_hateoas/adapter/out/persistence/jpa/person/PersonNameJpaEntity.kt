package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
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

