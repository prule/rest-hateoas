package com.example.rest_hateoas.adapter.out.persistence.jpa

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Embeddable
class KeyJpaEntity(
    var key: String = (UUID.randomUUID().toString()).replace("-", "")
)
