package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.common.LocalDateSerializer
import jakarta.persistence.*
import jakarta.validation.Valid
import kotlinx.serialization.Serializable
import java.time.LocalDate

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

