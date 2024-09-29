package com.example.rest_hateoas.adapter.`in`.rest

import com.example.rest_hateoas.adapter.`in`.rest.support.http.ZonedDateTimeSerializer
import jakarta.persistence.Column
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@Serializable
class ModelMetadataRestModel(

    @Serializable(with = ZonedDateTimeSerializer::class)
    val createdDate: ZonedDateTime? = null,

    @Serializable(with = ZonedDateTimeSerializer::class)
    val lastModifiedDate: ZonedDateTime? = null


) {
    companion object {
        fun empty(): ModelMetadataRestModel {
            return ModelMetadataRestModel(
                null,
                null
            )
        }
    }
}