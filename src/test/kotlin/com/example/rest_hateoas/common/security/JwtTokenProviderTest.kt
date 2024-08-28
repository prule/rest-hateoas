package com.example.rest_hateoas.common.security

import com.example.rest_hateoas.user.UserFixtures
import com.example.rest_hateoas.user.UserPrincipal
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class JwtTokenProviderTest {

    // Proper secret keys can be generated here https://www.devglan.com/online-tools/hmac-sha256-online

    @Test
    fun `username should be contained in token`() {
        // prepare
        val provider = JwtTokenProvider(
            UserDetailsServiceFake(UserPrincipal(UserFixtures.Users.Fred.user)),
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            9999
        )
        val expectedUsername = "test"
        // perform
        val token = provider.createToken(expectedUsername, listOf())
        val actualUsername = provider.getUsername(token)
        // verify
        actualUsername.shouldBeEqual(expectedUsername)
    }

    @Test
    fun `token should be valid`() {
        // prepare
        val provider = JwtTokenProvider(
            UserDetailsServiceFake(UserPrincipal(UserFixtures.Users.Fred.user)),
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            9999
        )
        // perform
        val token = provider.createToken("test", listOf())
        val validateToken = provider.validateToken(token)
        // verify
        validateToken.shouldBeTrue()
    }

}