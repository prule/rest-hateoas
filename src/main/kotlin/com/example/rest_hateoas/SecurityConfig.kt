package com.example.rest_hateoas

import com.example.rest_hateoas.errorhandling.RestExceptionHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.annotation.web.builders.*

@Configuration
class SecurityConfig(
//    jwtTokenProvider: JwtTokenProvider,
//    userDetailsService: UserDetailsService,
    restExceptionHandler: RestExceptionHandler,
    private val objectMapper: ObjectMapper
)
     {
//    private val jwtTokenProvider: JwtTokenProvider = jwtTokenProvider
//    private val userDetailsService: UserDetailsService = userDetailsService
    private val restExceptionHandler: RestExceptionHandler = restExceptionHandler

//    @Bean
//    @Throws(Exception::class)
//    override fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }

    @Throws(Exception::class)
    @Bean
    fun configure(): WebSecurityCustomizer {
       return WebSecurityCustomizer { web: WebSecurity ->

            web.ignoring().requestMatchers("/h2-console/**")
            web.ignoring().requestMatchers("/static/**")
            web.ignoring().requestMatchers("/*")
            web.ignoring().requestMatchers("/api/*/index")
            web.ignoring().requestMatchers("/api/1/auth/*")
        }
    }

//    @Throws(Exception::class)
//    protected fun configure(http: HttpSecurity):SecurityFilterChain {
//        http
//            .httpBasic().disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .cors() // so that the OPTIONS requests will return 200 instead of 403
//            .and()
//            .authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(
//                JwtConfigurer(
//                    jwtTokenProvider,
//                    restExceptionHandler,
//                    objectMapper
//                )
//            )
//    }
//
//    @Throws(Exception::class)
//    protected override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(authenticationProvider())
//    }
//
//    @Bean
//    fun authenticationProvider(): DaoAuthenticationProvider {
//        val authProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()
//        authProvider.setUserDetailsService(userDetailsService)
//        authProvider.setPasswordEncoder(encoder())
//        return authProvider
//    }
//
//    @Bean
//    fun encoder(): PasswordEncoder {
//        return BCryptPasswordEncoder(11)
//    }
}