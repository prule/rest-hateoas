package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.VersionedRepresentationModel
import java.time.LocalDate

open class PersonModel : VersionedRepresentationModel<PersonModel>() {
    var key: String? = null
    override var version: Long = 0

    var name: PersonName = PersonName()
    var address: Address = Address()
    var dateOfBirth: LocalDate? = null

    fun fromEntity(entity: Person): PersonModel {
        // fields that will always be set, regardless of fields parameters

        key = entity.key.key
        version = entity.version

        name = entity.name
        address = entity.address
        dateOfBirth = entity.dateOfBirth

        return this
    }

    fun toEntity(entity: Person) {
        checkVersion(entity)

        entity.name = name
        entity.address = address
        entity.dateOfBirth = dateOfBirth
    }

}
