package com.example.rest_hateoas.adapter.`in`.rest.index

import com.example.rest_hateoas.common.security.BearerToken
import com.example.rest_hateoas.common.security.JwtTokenFilter
import com.example.rest_hateoas.common.security.JwtTokenProvider
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.hamcrest.CoreMatchers.notNullValue
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
@ActiveProfiles(profiles = ["db-postgres-test", "db-init"])
class IndexControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
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
    fun `index page should contain links`() {
        given().contentType(ContentType.JSON)
            .`when`()
            .get("/api/1/index")
            .then()
            .statusCode(200)
            .body("_links", notNullValue())
            .body("_links.self", notNullValue())
            .body("_links.login", notNullValue())
    }
}