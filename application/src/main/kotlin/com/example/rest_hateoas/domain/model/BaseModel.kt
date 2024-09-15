package com.example.rest_hateoas.domain.model

import java.time.ZonedDateTime

open class BaseModel() {

    var id: Long? = null

    var version: Long? = null

    var createdDate: ZonedDateTime? = null

    var lastModifiedDate: ZonedDateTime? = null

    var createdBy: Long? = null

    var lastModifiedBy: Long? = null

    override fun toString(): String {
        return "BaseModel(id=$id, version=$version, createdDate=$createdDate, lastModifiedDate=$lastModifiedDate, createdBy=$createdBy, lastModifiedBy=$lastModifiedBy)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseModel

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}