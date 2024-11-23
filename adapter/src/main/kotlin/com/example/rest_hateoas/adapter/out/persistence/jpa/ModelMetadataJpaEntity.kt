package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.adapter.`in`.rest.support.http.InstantSerializer
import com.example.rest_hateoas.adapter.`in`.rest.support.http.ZonedDateTimeSerializer
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.ZonedDateTime

@Embeddable
@Serializable
class ModelMetadataJpaEntity(

    @CreatedDate
    @Column(insertable = true, updatable = false)
    @Serializable(with = InstantSerializer::class)
    var createdDate: Instant? = null,

    @LastModifiedDate
    @Serializable(with = InstantSerializer::class)
    var lastModifiedDate: Instant? = null,

    @CreatedBy
    @Column(insertable = true, updatable = false)
    var createdBy: Long? = null,

    @LastModifiedBy
    var lastModifiedBy: Long? = null
) {
    companion object {
        fun newInstance(): ModelMetadataJpaEntity {
            return ModelMetadataJpaEntity()
        }
    }
}



