package com.example.rest_hateoas.adapter.`in`.rest.support.http

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException : RuntimeException {
    constructor()

    constructor(message: String?) : super(message)
}
