package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.FieldVisibility
import com.example.rest_hateoas.common.Fields
import com.example.rest_hateoas.common.VersionedRepresentationModel
import com.example.rest_hateoas.user.UserGroup
import java.time.LocalDate

open class PersonModel : VersionedRepresentationModel<PersonModel>() {
    var key: String? = null
    override var version: Long = 0

    var name: PersonName = PersonName()
    var address: Address = Address()
    var dateOfBirth: LocalDate? = null

    fun fromEntity(entity: Person, fields: Fields): PersonModel {
        // fields that will always be set, regardless of fields parameters

        key = entity.key.key
        version = entity.version

        // fields dependent on fields parameters
        fields.set("name") { name = entity.name }
        fields.set("address") { address = entity.address }
        fields.set("dateOfBirth") { dateOfBirth = entity.dateOfBirth }

        return this
    }

    /**
     * The "fields" parameter controls which fields on the entity are set.
     * This could be controlled so only fields that the user has permission to
     * set are included - eg fields could be constructed or cleaned by security
     * logic before being passed here.
     */
    fun toEntity(entity: Person, fields: Fields) {
        checkVersion(entity)

        fields.set("name") { entity.name = name }
        fields.set("address") { entity.address = address }
        fields.set("dateOfBirth") { entity.dateOfBirth = dateOfBirth }
    }

    companion object {
        fun fieldVisibility(): FieldVisibility {
            return FieldVisibility(
                mapOf(
                    "name" to listOf(UserGroup.Group.ADMIN, UserGroup.Group.USER),
                    "address" to listOf(UserGroup.Group.ADMIN, UserGroup.Group.USER),
                    "dateOfBirth" to listOf(UserGroup.Group.ADMIN)
                )
            )
        }
    }
}
