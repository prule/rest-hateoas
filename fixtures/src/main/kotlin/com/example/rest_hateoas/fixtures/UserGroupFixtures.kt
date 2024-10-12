package com.example.rest_hateoas.fixtures

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.UserGroup

class UserGroupFixtures: Fixture<UserGroup> {
    enum class UserGroups(val group: UserGroup) {
        Admin(
            UserGroup(
                Key("admin"),
                "Administrator",
                "Administrator",
                true,
            )
        ),
        User(
            UserGroup(
                Key("user"),
                "User",
                "User",
                true,
            )
        ),
        Disabled(
            UserGroup(
                Key("disabled"),
                "Disabled",
                "Disabled",
                false,
            )
        )
    }

    override fun entries(): List<UserGroup> {
        return UserGroups.values().map { it.group }
    }
}
