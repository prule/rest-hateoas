package com.example.rest_hateoas.application.domain.model

import com.example.rest_hateoas.adapter.`in`.rest.support.http.ZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
open class BaseModel() {

    var id: Long? = null

    var version: Long? = null

    @Serializable(with = ZonedDateTimeSerializer::class)
    var createdDate: ZonedDateTime? = null

    @Serializable(with = ZonedDateTimeSerializer::class)
    var lastModifiedDate: ZonedDateTime? = null

    var createdBy: Long? = null

    var lastModifiedBy: Long? = null

}