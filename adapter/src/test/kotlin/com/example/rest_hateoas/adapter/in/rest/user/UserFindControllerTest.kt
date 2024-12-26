package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestModel
import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import com.example.rest_hateoas.adapter.`in`.rest.user.UserFindController.Companion.PATH_USER_ME
import com.example.rest_hateoas.adapter.jsonassert.AssertModelMetadata
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup
import com.example.rest_hateoas.fixtures.UserFixtures
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.xn32.json5k.Json5
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.encodeToString
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.LinkRelation
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.Instant

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles(profiles = ["db-postgres-test", "db-init"])
class UserFindControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider, @Autowired val objectMapper: ObjectMapper) {
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
    fun `should return apierror when user does not exist`() {
        val token = jwtTokenProvider.createToken("doesnotexist", listOf())
        given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get(PATH_USER_ME)
            .then()
            .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
            .body("apierror", notNullValue())
            .body("apierror.message", notNullValue())
    }

    @Test
    fun `should return apierror when no token is supplied`() {
        given().contentType(ContentType.JSON)
            .`when`()
            .get(PATH_USER_ME)
            .then()
            .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
            .body("apierror", notNullValue())
            .body("apierror.message", notNullValue())
    }

    @Test
    fun `should return me when logged in`() {
        val token = jwtTokenProvider.createToken(UserFixtures.Users.Fred.user.username.value, listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get(PATH_USER_ME)
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .body("apierror", nullValue())
            .extract().body().asString()

        val expected = UserRestModel(
            0L,
            "fred",
            User.Username("fred"),
            User.FirstName("Fred"),
            User.LastName("Doe"),
            User.Enabled(true),
            listOf(
                UserGroupRestModel(
                    UserGroup.Name("User"),
                    UserGroup.Description("User"),
                    UserGroup.Enabled(true)
                )
            ),
            ModelMetadataRestModel(
                Instant.now(),
                Instant.now()
            )
        )
        expected.add(
            Link.of("http://localhost:$port/api/1/user/fred", LinkRelation.of("self"))
        )

        println(objectMapper.writeValueAsString(EntityModel.of(expected)))
        val expectedResponseBody = """
            {
              "version": 0,
              "key": "fred",
              "username": "fred",
              "firstName": "Fred",
              "lastName": "Doe",
              "enabled": true,
              "groups": [
                {
                  "name": "User",
                  "description": "User",
                  "enabled": true
                }
              ],
              "metadata": {
                "createdDate": "2024-10-21T10:54:36.528794Z",
                "lastModifiedDate": "2024-10-21T10:54:36.539443Z"
              },
              "_links": {
                "self": {
                  "href": "http://localhost:$port/api/1/user/fred"
                }
              }
            }
        """.trimIndent()

        println(actualResponseBody)
//        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, true)
        AssertModelMetadata.assert(objectMapper.writeValueAsString(expected), actualResponseBody)

    }

}