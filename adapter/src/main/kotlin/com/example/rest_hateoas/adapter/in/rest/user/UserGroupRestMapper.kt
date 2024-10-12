package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestMapper
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.ModelMetadata
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserGroupRestMapper {

    fun fromDomain(value: UserGroup): UserGroupRestModel {
        val model = UserGroupRestModel(
            value.name,
            value.description,
            value.enabled,
        )

        // links
        // any logic could be applied here to control the presence of these links - user based permissions, or entity states etc
        return model
    }

    private fun hasPermission(rel: String): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    fun toDomain(key: String, value: UserUpdateRestModel): User {

        return User(
            Key(),
            value.username,
            UUID.randomUUID().toString(),
            value.firstName,
            value.lastName,
            value.enabled,
            listOf(),
            ModelMetadata()
        )

    }


}