package com.example.rest_hateoas.common

import org.hibernate.dialect.lock.OptimisticEntityLockException
import org.springframework.hateoas.RepresentationModel

abstract class VersionedRepresentationModel<T : RepresentationModel<out T>?> : RepresentationModel<T>() {
    fun checkVersion(model: AbstractEntity<*>) {
        if (model.version != this.version) {
            throw OptimisticEntityLockException(model, "Concurrent edit detected")
        }
    }

    abstract val version: Long
}
