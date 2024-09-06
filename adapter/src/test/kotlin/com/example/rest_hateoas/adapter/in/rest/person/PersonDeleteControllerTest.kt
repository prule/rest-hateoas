package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import com.example.rest_hateoas.adapter.jsonassert.AssertApiError
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person.PersonFixtures
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
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
class PersonDeleteControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
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
    fun `should delete person`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        // delete
        given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .delete("/api/1/persons/${PersonFixtures.Persons.Fred.person.key.key}")
            .then()
            .statusCode(HttpServletResponse.SC_NO_CONTENT)

        // check doesn't exist anymore
        given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get("/api/1/persons/${PersonFixtures.Persons.Fred.person.key.key}")
            .then()
            .statusCode(HttpServletResponse.SC_NOT_FOUND)

    }

    @Test
    fun `should return not found`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        // delete
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .delete("/api/1/persons/doesnotexist")
            .then()
            .statusCode(HttpServletResponse.SC_NOT_FOUND)
            .extract().body().asString()

        val expectedResponseBody = """
        {
            "apierror": {
                "status": "NOT_FOUND",
                "timestamp": "2024-09-05T15:26:02.309899+10:00",
                "message": "Not Found",
                "debugMessage": "Person Key(key='doesnotexist') not found",
                "subErrors": null
            }
        }
        """.trimIndent()

        AssertApiError.assert(expectedResponseBody, actualResponseBody)
    }

}