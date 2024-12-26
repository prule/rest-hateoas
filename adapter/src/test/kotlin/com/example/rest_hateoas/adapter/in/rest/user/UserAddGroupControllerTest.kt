package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.RestHateoasApplication
import com.example.rest_hateoas.adapter.`in`.rest.support.security.BearerToken
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenFilter
import com.example.rest_hateoas.adapter.`in`.rest.support.security.JwtTokenProvider
import com.example.rest_hateoas.adapter.`in`.rest.user.UserAddUserGroupController.Companion.ADD_GROUP_PATH
import com.example.rest_hateoas.adapter.jsonassert.AssertModelMetadata
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.fixtures.UserFixtures
import com.example.rest_hateoas.fixtures.UserGroupFixtures
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.servlet.http.HttpServletResponse
import org.hamcrest.CoreMatchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


// https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = arrayOf(RestHateoasApplication::class))
@Testcontainers
@EnableTransactionManagement
@ActiveProfiles(profiles = ["db-postgres-test", "db-init", "debug"])
class UserAddGroupControllerTest(@Autowired val jwtTokenProvider: JwtTokenProvider) {
    companion object {
        @Container
        @ServiceConnection
        var postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer(
            DockerImageName.parse("postgres:latest")
        ).withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("postgres")
            .withExposedPorts(5432)

//        @BeforeAll
//        fun beforeAll() {
//            postgreSQLContainer.start()
//        }
//
//        @AfterAll
//        fun afterAll() {
//            postgreSQLContainer.stop()
//        }

        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            println("configureProperties")
            println("spring.datasource.url ${postgreSQLContainer::getJdbcUrl}")
            println("spring.datasource.username  ${postgreSQLContainer.getUsername()}")
            println("spring.datasource.password ${postgreSQLContainer.getPassword()}")
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }


    }


    @LocalServerPort
    private val port: Int? = null

    @BeforeEach
    fun setUp() {
        RestAssured.port = port!!
    }

    @Rollback(false)
    @Test
    fun `add group`() {
        val token = jwtTokenProvider.createToken(UserFixtures.Users.Boss.user.username.value, listOf())
        val actualResponseBody = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .put(
                AddGroupToUserRequest(
                    UserFixtures.Users.Fred.user.key,
                    UserGroupFixtures.UserGroups.Admin.group.key
                ).toString()
            )
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .body("apierror", nullValue())
            .extract().body().asString()

        val expectedResponseBody = """
             {
              "version": 1,
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
                },
                {
                  "name": "Administrator",
                  "description": "Administrator",
                  "enabled": true
                }

              ],
              "metadata": {
                "createdDate": "2024-10-21T10:56:57.514462Z",
                "lastModifiedDate": "2024-10-21T10:56:57.517327Z"
              },
              "_links": {
                "self": {
                  "href": "http://localhost:${port}/api/1/user/fred"
                }
              }
            }
       """.trimIndent()

        println(actualResponseBody)

        AssertModelMetadata.assert(expectedResponseBody, actualResponseBody)

//        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, true)
    }

    class AddGroupToUserRequest(val userKey: Key, val groupKey: Key) {
        override fun toString(): String {
            return ADD_GROUP_PATH.replace("{userKey}", userKey.key).replace("{groupKey}", groupKey.key)
        }
    }
}