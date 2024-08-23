package com.example.rest_hateoas.authentication

import com.example.rest_hateoas.common.security.JwtTokenProvider
import com.example.rest_hateoas.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) {

    @PostMapping("/api/1/auth/login")
    fun login(@RequestBody resource: LoginRequestModel): LoginResponseModel {
        val username: String = resource.username
        val password: String = resource.password

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            val token: String = jwtTokenProvider.createToken(username, emptyList())
            return LoginResponseModel(username, token)
        } catch (e: org.springframework.security.core.AuthenticationException) {
            throw BadCredentialsException("Invalid username/password")
        }
    }

}
