package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.adapter.`in`.rest.support.security.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.time.ZonedDateTime
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "auditingDateTimeProvider")
class JpaAuditingConfiguration {
    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return AuditorAware {
            val principal = SecurityContextHolder.getContext().authentication?.principal
            Optional.ofNullable(
                if (principal != null) (principal as UserPrincipal).id else null
            )
        }
    }

    @Bean
    fun auditingDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of(ZonedDateTime.now()) }
    }
}
