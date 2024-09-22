package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.domain.model.Key

interface PersonDeleteUseCase {
    fun delete(key: Key)
}