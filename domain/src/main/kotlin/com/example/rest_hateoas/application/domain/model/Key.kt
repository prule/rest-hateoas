package com.example.rest_hateoas.application.domain.model

import java.util.*

class Key(
    var key: String = (UUID.randomUUID().toString()).replace("-", "")
)
