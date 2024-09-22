package com.example.rest_hateoas.adapter.out.persistence.jpa.person

import com.example.rest_hateoas.adapter.`in`.rest.support.http.LocalDateSerializer
import com.example.rest_hateoas.adapter.out.persistence.jpa.AbstractJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import jakarta.persistence.*
import jakarta.validation.Valid
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Entity(name = "person")
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

    ) : AbstractJpaEntity() {

}

