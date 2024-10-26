package com.example.rest_hateoas.application.port.`in`.user

interface UpdateUserUseCase {
    fun update(command: UpdateUserCommand)
}