package com.example.rest_hateoas.common.security

import com.example.rest_hateoas.user.UserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class UserDetailsServiceFake(val user: UserPrincipal): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return user
    }
}