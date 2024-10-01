package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.startsWith
import org.hamcrest.Matchers.`is`
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
//import org.testcontainers.shaded.org.hamcrest.Matchers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles(profiles = ["db-postgres-test", "db-init"])
class PersonSearchControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
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
    fun `should order search firstName ASC lastName ASC`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        given()
            .contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            // ?size=2&page=2&sort=name.lastName,asc&sort=name.firstName,desc&=
            .queryParam("size", 4)
            .queryParam("page", 0)
            .queryParam("sort", "name.firstName,asc")
            .queryParam("sort", "name.lastName,asc")
            .queryParam("filter", "sortable")
            .`when`()
            .get("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .body("_embedded.personRestModelList[0].name.firstName", equalTo("A"))
            .body("_embedded.personRestModelList[0].name.lastName", equalTo("A"))
            .body("_embedded.personRestModelList[1].name.firstName", equalTo("A"))
            .body("_embedded.personRestModelList[1].name.lastName", equalTo("B"))
            .body("_embedded.personRestModelList[2].name.firstName", equalTo("B"))
            .body("_embedded.personRestModelList[2].name.lastName", equalTo("A"))
            .body("_embedded.personRestModelList[3].name.firstName", equalTo("B"))
            .body("_embedded.personRestModelList[3].name.lastName", equalTo("B"))
    }

    @Test
    fun `should order search firstName ASC lastName DESC`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        given()
            .contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            // ?size=2&page=2&sort=name.lastName,asc&sort=name.firstName,desc&=
            .queryParam("size", 4)
            .queryParam("page", 0)
            .queryParam("sort", "name.firstName,asc")
            .queryParam("sort", "name.lastName,desc")
            .queryParam("filter", "sortable")
            .`when`()
            .get("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .body("_embedded.personRestModelList[0].name.firstName", equalTo("A"))
            .body("_embedded.personRestModelList[0].name.lastName", equalTo("B"))
            .body("_embedded.personRestModelList[1].name.firstName", equalTo("A"))
            .body("_embedded.personRestModelList[1].name.lastName", equalTo("A"))
            .body("_embedded.personRestModelList[2].name.firstName", equalTo("B"))
            .body("_embedded.personRestModelList[2].name.lastName", equalTo("B"))
            .body("_embedded.personRestModelList[3].name.firstName", equalTo("B"))
            .body("_embedded.personRestModelList[3].name.lastName", equalTo("A"))
    }

    @Test
    fun `should filter search`() {
        val token = jwtTokenProvider.createToken("boss", listOf())
        given()
            .contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            // ?size=2&page=2&sort=name.lastName,asc&sort=name.firstName,desc&=
            .queryParam("size", 20)
            .queryParam("page", 0)
            .queryParam("filter", "sortable")
            .`when`()
            .get("/api/1/persons")
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .body("_embedded.personRestModelList[0].key", startsWith("sortable"))
            .body("_embedded.personRestModelList[1].key", startsWith("sortable"))
            .body("_embedded.personRestModelList[2].key", startsWith("sortable"))
            .body("_embedded.personRestModelList[3].key", startsWith("sortable"))
            .body("_embedded.personRestModelList.size()", `is`(4))
    }

}