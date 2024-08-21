package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.LocalDateSerializer
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*
import jakarta.validation.Valid
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Entity
class Person(

    @Basic(optional = false)
    val key: Key,

    @Embedded
    @field:Valid
    var name: PersonName = PersonName(),

    @Embedded
    var address: Address = Address(),

    @Basic
    @Serializable(with = LocalDateSerializer::class)
    var dateOfBirth: LocalDate? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

) : AbstractEntity<Long>() {

}

