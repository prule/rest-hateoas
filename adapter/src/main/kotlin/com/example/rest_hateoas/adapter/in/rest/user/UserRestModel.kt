package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.domain.model.User
import org.springframework.hateoas.RepresentationModel


open class UserRestModel : RepresentationModel<UserRestModel>() {

    var version: Long = 0

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
