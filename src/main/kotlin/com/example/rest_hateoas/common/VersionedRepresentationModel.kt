package com.example.rest_hateoas.common

import com.example.rest_hateoas.adapter.out.persistence.jpa.AbstractJpaEntity
import org.hibernate.dialect.lock.OptimisticEntityLockException
import org.springframework.hateoas.RepresentationModel

abstract class VersionedRepresentationModel<T : RepresentationModel<out T>?> : RepresentationModel<T>() {
    fun checkVersion(model: AbstractJpaEntity) {
        if (model.version != this.version) {
            throw OptimisticEntityLockException(model, "Concurrent edit detected")
        }
    }

    abstract val version: Long?
}
