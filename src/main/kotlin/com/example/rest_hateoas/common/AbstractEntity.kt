package com.example.rest_hateoas.common

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaEntity
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
open class AbstractEntity(

    @Basic
    @Version
    var version: Long? = null,

    @CreatedDate
    @Column(insertable = true, updatable = false)
    @Serializable(with = ZonedDateTimeSerializer::class)
    var createdDate: ZonedDateTime? = null,

    @LastModifiedDate
    @Serializable(with = ZonedDateTimeSerializer::class)
    var lastModifiedDate: ZonedDateTime? = null,

    @CreatedBy
    @Column(insertable = true, updatable = false)
    var createdBy: Long? = null,

    @LastModifiedBy
    var lastModifiedBy: Long? = null

)


