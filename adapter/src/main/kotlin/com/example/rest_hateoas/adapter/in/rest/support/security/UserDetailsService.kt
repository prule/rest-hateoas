package com.example.rest_hateoas.adapter.`in`.rest.support.security

import com.example.rest_hateoas.adapter.out.persistence.jpa.user.UserJpaMapper
import com.example.rest_hateoas.adapter.out.persistence.jpa.user.UserSpringDataRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(val userRepository: UserSpringDataRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return UserPrincipal(user.id!!, UserJpaMapper.toDomain(user))
    }
}
