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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory
import jakarta.servlet.http.HttpServletResponse
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.hateoas.Link
import org.springframework.hateoas.LinkRelation
import org.springframework.hateoas.mediatype.MessageResolver
import org.springframework.hateoas.mediatype.hal.CurieProvider
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import org.springframework.hateoas.server.core.DefaultLinkRelationProvider
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.lang.reflect.Type
import java.time.Instant

// https://github.com/spring-projects/spring-hateoas/blob/17644464d999a52de3a1872612df556097ad58b3/src/test/java/org/springframework/hateoas/mediatype/hal/HalTestUtils.java#L51-L65
// https://github.com/spring-projects/spring-hateoas/issues/1306
// https://stackoverflow.com/questions/71128775/testing-spring-hateoas-application-with-representationmodelassembler

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles(profiles = ["db-postgres-test", "db-init"])
class UserFindControllerTest(
    @Autowired val jwtTokenProvider: JwtTokenProvider,
    @Autowired val objectMapper: ObjectMapper
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

        // use the same object mapper as used by spring boot so we get the same behavior
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            ObjectMapperConfig().jackson2ObjectMapperFactory(
                object : DefaultJackson2ObjectMapperFactory() {
                    override fun create(cls: Type, charset: String?): ObjectMapper {
                        return objectMapper
                    }
                }
            ))
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
        val actual = given().contentType(ContentType.JSON)
            .header(JwtTokenFilter.AUTH_HEADER, BearerToken.buildTokenHeaderValue(token))
            .`when`()
            .get(PATH_USER_ME)
            .then()
            .statusCode(HttpServletResponse.SC_OK)
            .extract()
            .body()
            .asString()

        val expected =
            UserRestModel(
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

        val objectMapper = ObjectMapper().apply {
            registerModule(JavaTimeModule())
            registerModule(KotlinModule.Builder().build())
            registerModule(Jackson2HalModule())
            setHandlerInstantiator(
                Jackson2HalModule.HalHandlerInstantiator(
                    DefaultLinkRelationProvider(),
                    CurieProvider.NONE,
                    MessageResolver.DEFAULTS_ONLY
                )
            )
        }

        val expectedJsonString = objectMapper.writeValueAsString(expected)

        AssertModelMetadata.assert(
            expectedJsonString,
            actual
        )

    }

}