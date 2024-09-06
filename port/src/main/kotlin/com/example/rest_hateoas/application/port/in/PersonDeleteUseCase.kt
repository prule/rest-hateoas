package com.example.rest_hateoas.application.port.`in`

import com.example.rest_hateoas.application.domain.model.Key

interface PersonDeleteUseCase {
    fun delete(key: Key)
}