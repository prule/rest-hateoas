package com.example.rest_hateoas.common

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Embeddable
class Key(
    var key: String = (UUID.randomUUID().toString()).replace("-", "")
)
