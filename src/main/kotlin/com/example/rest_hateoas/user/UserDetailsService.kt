package com.example.rest_hateoas.user

import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(val userRepository: UserJpaRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return UserPrincipal(user)
    }
}
