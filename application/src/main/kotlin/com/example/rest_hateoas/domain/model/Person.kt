package com.example.rest_hateoas.domain.model

import java.time.LocalDate

class Person(

    val key: Key,

    var name: PersonName,

    var address: PersonAddress,

    var dateOfBirth: LocalDate? = null,

    ) : BaseModel() {

    override fun toString(): String {
        return "Person(key=$key, name=$name, address=$address, dateOfBirth=$dateOfBirth)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Person

        return key == other.key
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + key.hashCode()
        return result
    }


}