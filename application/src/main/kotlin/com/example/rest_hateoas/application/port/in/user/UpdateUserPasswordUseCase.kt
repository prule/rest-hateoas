package com.example.rest_hateoas.application.port.`in`.user

interface UpdateUserPasswordUseCase {
    fun updatePassword(command: UpdateUserPasswordCommand)
}