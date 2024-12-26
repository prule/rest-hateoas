package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.domain.model.User

class AuthorizationService {
    fun canPerformAction(user: User, action: String, resource: Any): Boolean {
        // Domain logic for permissions
        return user.hasPermission(action, resource)
    }
}
