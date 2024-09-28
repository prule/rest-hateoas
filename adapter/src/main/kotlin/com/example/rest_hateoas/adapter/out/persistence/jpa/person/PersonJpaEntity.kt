package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.ModelMetadataJpaEntity
import jakarta.persistence.*
import jakarta.validation.Valid
import kotlinx.serialization.Serializable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Serializable
@Entity(name = "person")
@EntityListeners(AuditingEntityListener::class)
class PersonJpaEntity(

    @Column(nullable = false, unique = true)
    val key: KeyJpaEntity,

    @Embedded
    @field:Valid
    var name: PersonNameJpaEntity,

    @Embedded
    var address: PersonAddressJpaEntity,

    @Basic
    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Basic
    @Version
    var version: Long = 0,

    @Embedded
    var metadata: ModelMetadataJpaEntity = ModelMetadataJpaEntity()
) {

}

