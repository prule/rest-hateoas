package com.example.rest_hateoas.domain.model

import java.time.ZonedDateTime

data class ModelMetadata(

    var version: Long = 0,
    val createdDate: ZonedDateTime? = null,
    val lastModifiedDate: ZonedDateTime? = null,
    val createdBy: Long? = null,
    val lastModifiedBy: Long? = null

)