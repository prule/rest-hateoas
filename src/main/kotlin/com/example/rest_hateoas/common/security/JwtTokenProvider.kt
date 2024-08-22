package com.example.rest_hateoas.common.security

import com.example.rest_hateoas.user.UserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    private val userDetailsService: UserDetailsService,
    @Value("\${security.jwt.token.secret}")
    private val secretKey: String,
    @Value("\${security.jwt.token.expiry}")
    private val validityInMilliseconds: Long
) {

    fun createToken(username: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().add("roles", roles).subject(username).build()
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(now)
            .expiration(validity)
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities())
    }

    fun getUsername(token: String?): String {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .payload
            .getSubject()
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken: String? = req.getHeader(AUTH_HEADER)
        if (bearerToken != null && bearerToken.startsWith(BEAR_TOKEN_PREFIX)) {
            return bearerToken.substring(BEAR_TOKEN_PREFIX.length)
        }
        return null
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
                .build()
                .parseSignedClaims(token)

            return claims.payload.expiration.after(Date())
        } catch (e: JwtException) {
            throw InvalidAuthenticationTokenException("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw InvalidAuthenticationTokenException("Expired or invalid JWT token")
        }
    }

    companion object {
        const val BEAR_TOKEN_PREFIX: String = "Bearer "
        const val AUTH_HEADER: String = "Authorization"
    }
}