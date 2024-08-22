package com.example.rest_hateoas.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(userRepository: UserRepository) : UserDetailsService {
    private val userRepository: UserRepository = userRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return UserPrincipal(user)
    }
}
