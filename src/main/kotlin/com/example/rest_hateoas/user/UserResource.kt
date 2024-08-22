package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.VersionedRepresentationModel
import com.example.rest_hateoas.person.PersonController
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder


open class UserResource : VersionedRepresentationModel<UserResource>() {

    override var version: Long = 0

    private var key: String? = null
    private var username: String? = null

    private var firstName: String? = null
    private var lastName: String? = null

    private var enabled = false

    fun fromModel(model: User): UserResource {

        key = model.key.key
        username = model.username

        firstName = model.firstName
        lastName = model.lastName

        enabled = model.enabled

        return this
    }

}
