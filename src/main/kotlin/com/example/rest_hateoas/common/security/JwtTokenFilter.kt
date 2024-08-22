package com.example.rest_hateoas.common.security

import com.example.rest_hateoas.common.errorhandling.RestExceptionHandler
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val restExceptionHandler: RestExceptionHandler,
    private val objectMapper: ObjectMapper
) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val token = resolveToken(request as HttpServletRequest)

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val auth = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().setAuthentication(auth)
            }
        } catch (e: InvalidAuthenticationTokenException) {
            val responseEntity: ResponseEntity<Any> = restExceptionHandler.handleInvalidAuthenticationToken(e, null)
            val httpServletResponse: HttpServletResponse = response as HttpServletResponse
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(responseEntity))
            return
        }

        filterChain.doFilter(request, response)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken: String? = req.getHeader(AUTH_HEADER)
        if (bearerToken != null && bearerToken.startsWith(BEAR_TOKEN_PREFIX)) {
            return bearerToken.substring(BEAR_TOKEN_PREFIX.length)
        }
        return null
    }

    companion object {
        const val BEAR_TOKEN_PREFIX: String = "Bearer "
        const val AUTH_HEADER: String = "Authorization"
    }

}