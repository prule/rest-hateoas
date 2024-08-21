package com.example.rest_hateoas

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
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware {
            Optional.ofNullable(
//                if (SecurityContextHolder.getContext().authentication != null)
                SecurityContextHolder.getContext().authentication?.name
//                else
//                    null
            )
        }
    }

    @Bean
    fun auditingDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of(ZonedDateTime.now()) }
    }
}
