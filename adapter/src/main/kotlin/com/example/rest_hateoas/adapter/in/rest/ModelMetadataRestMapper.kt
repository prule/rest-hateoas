package com.example.rest_hateoas.adapter.`in`.rest

import com.example.rest_hateoas.domain.model.ModelMetadata

class ModelMetadataRestMapper {
    companion object {
        fun fromDomain(value: ModelMetadata): ModelMetadataRestModel{
            return ModelMetadataRestModel(
                value.createdDate,
                value.lastModifiedDate,
            )
        }
    }
}