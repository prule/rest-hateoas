package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.VersionedRepresentationModel


open class UserModel : VersionedRepresentationModel<UserModel>() {

    override var version: Long = 0

    var key: String? = null
    var username: String? = null

    var firstName: String? = null
    var lastName: String? = null

    var enabled = false

    fun fromModel(model: User): UserModel {

        key = model.key.key
        username = model.username

        firstName = model.firstName
        lastName = model.lastName

        enabled = model.enabled

        return this
    }

}
