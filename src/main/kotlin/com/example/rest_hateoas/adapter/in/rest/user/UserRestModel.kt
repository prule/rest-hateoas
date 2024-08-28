package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserJpaEntity
import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.common.VersionedRepresentationModel


open class UserRestModel : VersionedRepresentationModel<UserRestModel>() {

    override var version: Long = 0

    var key: String? = null
    var username: String? = null

    var firstName: String? = null
    var lastName: String? = null

    var enabled = false

    fun fromModel(model: User): UserRestModel {

        key = model.key.key
        username = model.username

        firstName = model.firstName
        lastName = model.lastName

        enabled = model.enabled

        return this
    }

}
