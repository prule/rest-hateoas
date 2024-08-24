package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.security.BearerToken
import com.example.rest_hateoas.common.security.JwtTokenFilter
import com.example.rest_hateoas.common.security.JwtTokenProvider
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles(profiles = ["db-postgres-test","db-init"])
class UserControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
    companion object {
        @Container
        @ServiceConnection
        var postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer(
            DockerImageName.parse("postgres:latest")
        )
    }

    @LocalServerPort
    private val port: Int? = null

    @BeforeEach
    fun setUp() {
        RestAssured.port = port!!
    }

    @Test
    fun `should return null when not logged in`() {
        val token = jwtTokenProvider.createToken("doesnotexist", listOf())
        given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get("/api/1/user/me")
            .then()
            .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
            .body("apierror", notNullValue())
            .body("apierror.message", notNullValue())

    }
}