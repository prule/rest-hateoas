package com.example.rest_hateoas.application.port.`in`.user

import com.example.rest_hateoas.domain.model.User

class UpdateUserPasswordCommand(
    val user: User,
    val newPassword: String
)