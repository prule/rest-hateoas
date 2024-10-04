package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestMapper
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.ModelMetadata
import com.example.rest_hateoas.domain.model.User
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserRestMapper {

    fun fromDomain(value: User): UserRestModel {
        val model = UserRestModel(
            value.metaData.version,
            value.key.key,
            value.username,
            value.firstName,
            value.lastName,
            value.enabled,
            ModelMetadataRestMapper.fromDomain(value.metaData)
        )

        // links
        // any logic could be applied here to control the presence of these links - user based permissions, or entity states etc
        model.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FindUserController::class.java).find(value.key.key)
            ).withSelfRel()
        )
        return model
    }

    private fun hasPermission(rel: String): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    fun toNewDomain(value: UserCreateRestModel): User {

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