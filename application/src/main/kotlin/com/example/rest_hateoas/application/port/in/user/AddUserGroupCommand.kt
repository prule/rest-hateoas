package com.example.rest_hateoas.application.port.`in`.user

import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup

data class AddUserGroupCommand(
    val user: User,
    val group: UserGroup
) {
    init {
        // user cannot already have this group
        check(!user.hasGroup(group)) {
            "User ${user.id} already has group ${group}"
        }
    }
}
