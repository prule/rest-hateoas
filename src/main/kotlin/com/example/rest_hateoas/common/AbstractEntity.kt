package com.example.rest_hateoas.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
open class AbstractEntity<U>(
) {

    @Basic
    @Version
    var version: Long = 0

    @CreatedDate
    var createdDate: ZonedDateTime? = null

    @LastModifiedDate
    var lastModifiedDate: ZonedDateTime? = null

    @CreatedBy
    var createdBy: U? = null

    @LastModifiedBy
    var lastModifiedBy: U? = null

}


