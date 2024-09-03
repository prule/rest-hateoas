package com.example.rest_hateoas.application.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Key(
    var key: String = (UUID.randomUUID().toString()).replace("-", "")
)
