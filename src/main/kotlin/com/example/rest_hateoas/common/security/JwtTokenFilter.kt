package com.example.rest_hateoas.common.security

import com.example.rest_hateoas.common.errorhandling.RestExceptionHandler
import com.example.rest_hateoas.common.http.ResponseWriter
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
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
            } else {
                writeUnauthorizedResponse(
                    restExceptionHandler.handleInvalidAuthenticationToken(InvalidAuthenticationTokenException("No token"), null),
                    response
                )
                return
            }
        } catch (e: InvalidAuthenticationTokenException) {
            writeUnauthorizedResponse(restExceptionHandler.handleInvalidAuthenticationToken(e, null), response)
            return
        } catch (e: Exception) {
            // catch all scenario that won't expose specific details
            writeUnauthorizedResponse(
                restExceptionHandler.handleBadCredentials(BadCredentialsException("Unexpected", e), null),
                response
            )
            return
        }

        filterChain.doFilter(request, response)
    }

    fun writeUnauthorizedResponse(responseEntity: ResponseEntity<Any>, response: ServletResponse) {
        ResponseWriter(objectMapper).write(
            response as HttpServletResponse,
            HttpServletResponse.SC_UNAUTHORIZED,
            responseEntity
        )
    }

    fun resolveToken(req: HttpServletRequest): String? {
        return BearerToken.extractTokenFromHeaderValue(req.getHeader(AUTH_HEADER))
    }

    companion object {
        const val AUTH_HEADER: String = "Authorization"

    }

}