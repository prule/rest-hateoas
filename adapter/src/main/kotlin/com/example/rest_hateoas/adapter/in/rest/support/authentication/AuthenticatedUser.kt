package com.example.rest_hateoas.adapter.`in`.rest.support.authentication

import com.example.rest_hateoas.adapter.out.persistence.jpa.user.UserGroupJpaEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(val authentication: Authentication) {

    val principal: UserDetails get() = authentication.principal as UserDetails

    // todo change this parameter
    fun hasGroup(group: UserGroupJpaEntity.Group): Boolean {
        return authentication.authorities.stream().anyMatch { g: GrantedAuthority? -> g?.getAuthority() == group.id }
    }

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
