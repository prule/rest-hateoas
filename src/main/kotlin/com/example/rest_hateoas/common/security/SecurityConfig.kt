package com.example.rest_hateoas.common.security

import com.example.rest_hateoas.common.errorhandling.RestExceptionHandler
import com.example.rest_hateoas.user.UserDetailsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService,
    private val restExceptionHandler: RestExceptionHandler,
    private val objectMapper: ObjectMapper
) {

    /**
     * Set up unauthenticated paths.
     */
    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/h2-console/**",
                "/static/**",
                "/api/*/index",
                "/api/*/auth/*",
            )
        }
    }

    /**
     * Set up the filter chain - authentication, jwt token filter, and stateless
     */
    // tag::filterChain[]
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests(Customizer { authz ->
            authz.anyRequest().authenticated()
        })
            // JWT token filter
            .addFilterBefore(
                JwtTokenFilter(jwtTokenProvider, restExceptionHandler, objectMapper),
                UsernamePasswordAuthenticationFilter::class.java
            )
            // no state on the server (not using sessions)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            // set up cors
            // https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS#
            .cors(Customizer { corsConfigurationSource() })
            // disable csrf since JWT is being used
            // https://stackoverflow.com/questions/52363487/what-is-the-reason-to-disable-csrf-in-spring-boot-web-application
            .csrf(Customizer { csrf -> csrf.disable() })
            .build()
    }
    // end::filterChain[]

    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        // TODO inject allowed origins
        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    // tag::authenticationProvider[]
    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }
    // end::authenticationProvider[]

    // tag::encoder[]
    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    // end::encoder[]

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

}