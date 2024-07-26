package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.DateSerializer
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Entity
class Person(

    @NotNull
    @Embedded
    var key: Key = Key(),

    @Embedded
    @Valid
    var name: PersonName = PersonName(),

    @Embedded
    var address: Address = Address(),

    @Basic
    @Serializable(with = DateSerializer::class)
    var dateOfBirth: LocalDate? = null
) : AbstractEntity<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

}

