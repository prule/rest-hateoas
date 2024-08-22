package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.VersionedRepresentationModel
import java.time.LocalDate

open class PersonModel : VersionedRepresentationModel<PersonModel>() {

    override var version: Long = 0

    var key: String? = null

    var name: PersonName = PersonName()
    var address: Address = Address()
    var dateOfBirth: LocalDate? = null

    fun fromEntity(entity: Person): PersonModel {

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
