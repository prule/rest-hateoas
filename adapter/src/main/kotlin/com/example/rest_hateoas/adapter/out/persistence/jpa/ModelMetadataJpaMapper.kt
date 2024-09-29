package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.domain.model.ModelMetadata

class ModelMetadataJpaMapper {
    companion object {
        fun toDomain(value: ModelMetadataJpaEntity, version: Long = 0): ModelMetadata {
            return ModelMetadata(
                version,
                value.createdDate,
                value.lastModifiedDate,
                value.createdBy,
                value.lastModifiedBy,
            )
        }

        fun toJpaEntity(value: ModelMetadata): ModelMetadataJpaEntity {
            return ModelMetadataJpaEntity(
                value.createdDate,
                value.lastModifiedDate,
                value.createdBy,
                value.lastModifiedBy,
            )
        }
    }
}