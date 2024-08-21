package com.example.rest_hateoas

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.server.ErrorPage
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableSpringDataWebSupport
internal class WebConfiguration(
    @Value("\${app.cors.origins:}") var corsOrigins: Array<String>?
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        if (corsOrigins != null && corsOrigins!!.size > 0) {
            registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(*corsOrigins!!)
                .exposedHeaders("app-version")
                .allowedMethods(
                    HttpMethod.OPTIONS.name(),
                    HttpMethod.HEAD.name(),
                    HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name()
                )
        }
        super.addCorsMappings(registry)
    }

    // Configure 404 to serve the SPA
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/notFound").setViewName("forward:/index.html")
    }

    @Bean
    fun containerCustomizer(): WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
        return WebServerFactoryCustomizer { container: ConfigurableServletWebServerFactory ->
            container.addErrorPages(
                ErrorPage(HttpStatus.NOT_FOUND, "/notFound")
            )
        }
    }
}