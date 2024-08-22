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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
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


//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }

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

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests(Customizer { authz ->
            authz.anyRequest().authenticated()
        }).addFilterBefore(
            JwtTokenFilter(jwtTokenProvider, restExceptionHandler, objectMapper),
            UsernamePasswordAuthenticationFilter::class.java
        ).sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors(Customizer { corsConfigurationSource() })
            .csrf(Customizer { csrf -> csrf.disable() })

        return http.build()
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

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}