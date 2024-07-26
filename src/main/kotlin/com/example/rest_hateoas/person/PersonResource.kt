package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.Fields
import com.example.rest_hateoas.common.VersionedRepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import java.time.LocalDate

open class PersonResource() : VersionedRepresentationModel<PersonResource>() {
    var key: String? = null
    override var version: Long = 0

    var name: PersonName = PersonName()
    var address: Address = Address()
    var dateOfBirth: LocalDate? = null

    fun fromModel(model: Person, fields: Fields): PersonResource {
        // fields that will always be set, regardless of fields parameters

        key = model.key.key
        version = model.version

        // fields dependent on fields parameters
        fields.set("name") { name = model.name }
        fields.set("address") { address = model.address }
        fields.set("dateOfBirth") { dateOfBirth = model.dateOfBirth }

        // links
        add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonApi::class.java).find(model.key.key, fields))
                .withSelfRel()
        )

        return this
    }

    fun toModel(model: Person) {
        checkVersion(model)

        model.name = name
        model.address = address
        model.dateOfBirth = dateOfBirth
    }
}
