package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestModel
import com.example.rest_hateoas.domain.model.User
import kotlinx.serialization.Serializable
import org.springframework.hateoas.RepresentationModel

@Serializable
open class UserRestModel (
    val version: Long = 0,
    val key: String? = null,

    val username: String,
    val firstName: String? = null,
    val lastName: String? = null,

    val enabled: Boolean = false,

    val groups: List<UserGroupRestModel> = listOf(),

    val metadata: ModelMetadataRestModel

): RepresentationModel<UserRestModel>() {


}
