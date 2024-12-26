package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestModel
import com.example.rest_hateoas.domain.model.User
import com.jayway.jsonpath.internal.function.sequence.Last
import kotlinx.serialization.Serializable
import org.springframework.hateoas.Link
import org.springframework.hateoas.Links
import org.springframework.hateoas.RepresentationModel

@Serializable
open class UserRestModel (
    val version: Long = 0,
    val key: String? = null,

    val username: User.Username,
    val firstName: User.FirstName? = null,
    val lastName: User.LastName? = null,
    val enabled: User.Enabled = User.Enabled(false),

    val groups: List<UserGroupRestModel> = listOf(),

    val metadata: ModelMetadataRestModel,

): RepresentationModel<UserRestModel>() {


}
