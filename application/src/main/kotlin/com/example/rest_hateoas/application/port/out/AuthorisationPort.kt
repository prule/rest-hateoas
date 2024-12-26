package com.example.rest_hateoas.application.port.out

interface AuthorisationPort {
    fun checkPermission(userId: String, action: String, resource: String): Boolean
}