package com.example.rest_hateoas.user

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(val authentication: Authentication? = null) {

    private val principal: UserDetails
        get() = authentication!!.principal as UserDetails

    fun hasGroup(group: UserGroup.Group): Boolean {
        if (authentication != null) {
            return authentication.authorities.stream().anyMatch { g: GrantedAuthority? -> g?.getAuthority() == group.id }
        }
        return false
    }

    val isAdmin: Boolean get() = hasGroup(UserGroup.Group.ADMIN)

    fun `is`(username: String): Boolean {
        return principal.getUsername() == username
    }

    val username: String get() = principal.getUsername()

    companion object {
        val instance: AuthenticatedUser?
            get() {
                val authentication: Authentication = SecurityContextHolder.getContext().getAuthentication()
                if (authentication.isAuthenticated) {
                    return AuthenticatedUser(authentication)
                }
                return null
            }
    }
}
