package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.adapter.`in`.rest.support.http.ZonedDateTimeSerializer
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
open class AbstractJpaEntity() {

    @Basic
    @Version
    var version: Long? = null

    @CreatedDate
    @Column(insertable = true, updatable = false)
    @Serializable(with = ZonedDateTimeSerializer::class)
    var createdDate: ZonedDateTime? = null

    @LastModifiedDate
    @Serializable(with = ZonedDateTimeSerializer::class)
    var lastModifiedDate: ZonedDateTime? = null

    @CreatedBy
    @Column(insertable = true, updatable = false)
    var createdBy: Long? = null

    @LastModifiedBy
    var lastModifiedBy: Long? = null

}


