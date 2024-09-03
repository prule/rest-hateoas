package com.example.rest_hateoas.application.domain.model

import java.time.ZonedDateTime

open class BaseModel() {

    var id: Long? = null

    var version: Long? = null

    var createdDate: ZonedDateTime? = null

    var lastModifiedDate: ZonedDateTime? = null

    var createdBy: Long? = null

    var lastModifiedBy: Long? = null

}