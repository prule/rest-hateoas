package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.application.port.`in`.user.UpdateUserCommand
import com.example.rest_hateoas.domain.model.Person

interface PersonUpdateUseCase {
    fun update(command: UpdatePersonCommand)
}