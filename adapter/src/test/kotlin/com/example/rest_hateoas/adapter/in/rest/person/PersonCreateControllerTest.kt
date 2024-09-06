package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import com.example.rest_hateoas.adapter.jsonassert.AssertApiError
import com.example.rest_hateoas.adapter.jsonassert.Customizations
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher
import org.skyscreamer.jsonassert.comparator.CustomComparator
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
import java.time.LocalDate

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles(profiles = ["db-postgres-test", "db-init"])
class PersonCreateControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
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
    fun `should create person`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(
                PersonCreateRestModel(
                    PersonNameRestModel("firstname", "lastname", "othernames"),
                    PersonAddressRestModel("line1", "line2", "city", "state", "country", "postcode"),
                    LocalDate.of(2024, 8, 24)
                )
            )
            .`when`()
            .post("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_CREATED)
            .extract().body().asString()

        val expectedResponseBody = """
            {
                "version": 0,
                "key": "<key goes here>",
                "name": {
                    "firstName": "firstname",
                    "lastName": "lastname",
                    "otherNames": "othernames"
                },
                "address": {
                    "line1": "line1",
                    "line2": "line2",
                    "city": "city",
                    "state": "state",
                    "country": "country",
                    "postcode": "postcode"
                },
                "dateOfBirth": "2024-08-24",
                "_links": {
                    "self": {
                        "href": "http://localhost:$port/api/1/persons/"
                    },
                    "persons-update": {
                        "href": "http://localhost:$port/api/1/persons/"
                    },
                    "persons-delete": {
                        "href": "http://localhost:$port/api/1/persons/"
                    }
                }
            }
        """.trimIndent()

        JSONAssert.assertEquals(
            expectedResponseBody, actualResponseBody,
            // need to account for the random key in the response
            CustomComparator(
                JSONCompareMode.STRICT,
                Customizations.link("_links.*.href", "http://localhost:$port/api/1/persons/"),
                Customizations.key("key")
            )
        )

    }

    @Test
    fun `should return validation error`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(
                PersonCreateRestModel(
                    PersonNameRestModel("", "lastname", "othernames"), // firstname should not be blank
                    PersonAddressRestModel("line1", "line2", "city", "state", "country", "postcode"),
                    LocalDate.of(2024, 8, 24)
                )
            )
            .`when`()
            .post("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_BAD_REQUEST)
            .extract().body().asString()

        val expectedResponseBody = """
            {
                "apierror": {
                    "status": "BAD_REQUEST",
                    "timestamp": "2024-09-05T12:30:37.407567+10:00",
                    "message": "Validation error",
                    "debugMessage": null,
                    "subErrors": [
                        {
                            "object": "personCreateRestModel",
                            "field": "name.firstName",
                            "rejectedValue": "",
                            "message": "must not be blank"
                        }
                    ]
                }
            }
        """.trimIndent()

        AssertApiError.assert(expectedResponseBody, actualResponseBody)

    }


}