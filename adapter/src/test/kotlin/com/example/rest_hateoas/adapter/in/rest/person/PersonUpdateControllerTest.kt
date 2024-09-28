package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import com.example.rest_hateoas.adapter.jsonassert.AssertApiError
import com.example.rest_hateoas.adapter.jsonassert.AssertModelMetadata
import com.example.rest_hateoas.adapter.jsonassert.Customizations
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person.PersonFixtures
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
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
class PersonUpdateControllerTest(
    @Autowired val jwtTokenProvider: JwtTokenProvider,
    @Autowired val personRestMapper: PersonRestMapper
) {
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
    fun `should update person`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(
                PersonUpdateRestModel(
                    0,
                    PersonNameRestModel("firstname'", "lastname'", "othernames'"),
                    PersonAddressRestModel("line1'", "line2'", "city'", "state'", "country'", "postcode'"),
                    LocalDate.of(2025, 8, 24)
                )
            )
            .`when`()
            .put("/api/1/persons/${PersonFixtures.Persons.Fred.person.key.key}")
            .then()
            .statusCode(HttpServletResponse.SC_CREATED)
            .extract().body().asString()

        val expectedResponseBody = """
            {
                "version": 1,
                "key": "fred",
                "name": {
                    "firstName": "firstname'",
                    "lastName": "lastname'",
                    "otherNames": "othernames'"
                },
                "address": {
                    "line1": "line1'",
                    "line2": "line2'",
                    "city": "city'",
                    "state": "state'",
                    "country": "country'",
                    "postcode": "postcode'"
                },
                "dateOfBirth": "2025-08-24",
                 "metadata": {
                    "createdDate": "2024-09-28T22:58:56.443439+10:00",
                    "lastModifiedDate": "2024-09-28T22:58:56.443439+10:00"
                 },
                 "_links": {
                    "self": {
                        "href": "http://localhost:$port/api/1/persons/fred"
                    },
                    "persons-update": {
                        "href": "http://localhost:$port/api/1/persons/fred"
                    },
                    "persons-delete": {
                        "href": "http://localhost:$port/api/1/persons/fred"
                    }
                }
            }
        """.trimIndent()

        AssertModelMetadata.assert(expectedResponseBody, actualResponseBody)

    }

    @Test
    fun `should return validation error`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val personRestModel = personRestMapper.fromDomain(PersonFixtures.Persons.Fred.person)
        personRestModel.name.firstName = ""

        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(personRestModel)
            .`when`()
            .put("/api/1/persons/${PersonFixtures.Persons.Fred.person.key.key}")
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
                            "object": "personUpdateRestModel",
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

    @Test
    fun `should return conflict when version is not correct`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val personRestModel = personRestMapper.fromDomain(PersonFixtures.Persons.Fred.person)
        // set version to an incorrect value
        personRestModel.version = -100

        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(personRestModel)
            .`when`()
            .put("/api/1/persons/${PersonFixtures.Persons.Fred.person.key.key}")
            .then()
            .statusCode(HttpServletResponse.SC_CONFLICT)
            .extract().body().asString()

        val expectedResponseBody = """
            {
                "apierror": {
                    "status": "CONFLICT",
                    "timestamp": "2024-09-05T12:02:12.246711+10:00",
                    "message": "Concurrent Edit Detected",
                    "debugMessage": "Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect) : [com.example.rest_hateoas.adapter.out.persistence.jpa.person.PersonJpaEntity#1]",
                    "subErrors": null
                }
            }
        """.trimIndent()

        AssertApiError.assert(expectedResponseBody, actualResponseBody, Customizations.conflict("apierror.debugMessage"))

    }


}