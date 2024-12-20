package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import com.example.rest_hateoas.adapter.jsonassert.AssertModelMetadata
import com.example.rest_hateoas.fixtures.UserFixtures
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
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
class UserFindControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
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
            .get("/api/1/user/me")
            .then()
            .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
            .body("apierror", notNullValue())
            .body("apierror.message", notNullValue())
    }

    @Test
    fun `should return apierror when no token is supplied`() {
        given().contentType(ContentType.JSON)
            .`when`()
            .get("/api/1/user/me")
            .then()
            .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
            .body("apierror", notNullValue())
            .body("apierror.message", notNullValue())
    }

    @Test
    fun `should return me when logged in`() {
        val token = jwtTokenProvider.createToken(UserFixtures.Users.Fred.user.username, listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get("/api/1/user/me")
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .body("apierror", nullValue())
            .extract().body().asString()

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
        AssertModelMetadata.assert(expectedResponseBody, actualResponseBody)

    }

}