package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.ModelMetadataRestMapper
import com.example.rest_hateoas.application.port.`in`.user.UpdateUserCommand
import com.example.rest_hateoas.domain.model.User
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service

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
            value.groups.map { UserGroupRestMapper().fromDomain(it) },
            ModelMetadataRestMapper.fromDomain(value.metaData),
        )
        // links
        // any logic could be applied here to control the presence of these links - user based permissions, or entity states etc
        model.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserFindController::class.java).find(value.key.key)
            ).withSelfRel()
        )
        return model
    }

    private fun hasPermission(rel: String): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    fun toUpdateUserCommand(user: User, value: UserUpdateRestModel): UpdateUserCommand {

        return UpdateUserCommand(
            user,
            UpdateUserCommand.UpdateUserFields(
                value.username,
                value.firstName,
                value.lastName,
                value.enabled,
                value.version,
            )
        )

    }

}