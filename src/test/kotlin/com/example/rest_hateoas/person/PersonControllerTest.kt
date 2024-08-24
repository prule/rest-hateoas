package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Address
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
import org.skyscreamer.jsonassert.JSONAssert
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
@ActiveProfiles(profiles = ["db-postgres-test","db-init"])
class PersonControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
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
    fun `should search people`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_OK)
    }

    @Test
    fun `should find person`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get("/api/1/persons/${PersonFixtures.Persons.Fred.person.key.key}")
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .extract().body().asString()

        val expectedResponseBody = """
            {
                "version": 0,
                "key": "fred",
                "name": {
                    "firstName": "Fred",
                    "lastName": "Flinstone",
                    "otherNames": "Freddy"
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

        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, true)

    }

    @Test
    fun `should create person`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(PersonModel(0,"should_create_person", PersonName("firstname","lastname","othernames"), Address("line1","line2","city","state","country","postcode"), LocalDate.of(2024,8,24)))
            .`when`()
            .post("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_CREATED)
            .extract().body().asString()

        val expectedResponseBody = """
            {
                "version": 0,
                "key": "should_create_person",
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
                        "href": "http://localhost:$port/api/1/persons/should_create_person"
                    },
                    "persons-update": {
                        "href": "http://localhost:$port/api/1/persons/should_create_person"
                    },
                    "persons-delete": {
                        "href": "http://localhost:$port/api/1/persons/should_create_person"
                    }
                }
            }
        """.trimIndent()

        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, true)

    }

    @Test
    fun `should update person`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .body(PersonModel(0,null, PersonName("firstname'","lastname'","othernames'"), Address("line1'","line2'","city'","state'","country'","postcode'"), LocalDate.of(2025,8,24)))
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

        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, true)

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

}