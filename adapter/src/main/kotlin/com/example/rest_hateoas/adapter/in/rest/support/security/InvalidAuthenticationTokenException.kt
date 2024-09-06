package com.example.rest_hateoas.adapter.`in`.rest.support.security

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InvalidAuthenticationTokenException(message: String) : RuntimeException(message)
