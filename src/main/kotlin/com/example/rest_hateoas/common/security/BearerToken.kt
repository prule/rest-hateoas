package com.example.rest_hateoas.common.security

class BearerToken {
    companion object {
        const val BEARER_TOKEN_PREFIX: String = "Bearer "

        fun extractTokenFromHeaderValue(value: String?): String? {
            if (value != null && value.startsWith(BEARER_TOKEN_PREFIX)) {
                return value.substring(BEARER_TOKEN_PREFIX.length)
            }
            return null
        }

        fun buildTokenHeaderValue(token: String): String {
            return BEARER_TOKEN_PREFIX + token
        }
    }
}