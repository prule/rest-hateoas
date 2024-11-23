package com.example.rest_hateoas.domain.model

import java.time.Instant
import java.time.ZonedDateTime

data class ModelMetadata(

    var version: Long = 0,
    val createdDate: Instant? = null,
    val lastModifiedDate: Instant? = null,
    val createdBy: Long? = null,
    val lastModifiedBy: Long? = null

)