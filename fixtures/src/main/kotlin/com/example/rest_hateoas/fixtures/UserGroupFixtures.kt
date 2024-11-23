package com.example.rest_hateoas.fixtures

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.UserGroup

class UserGroupFixtures : Fixture<UserGroup> {
    enum class UserGroups(val group: UserGroup) {
        Admin(
            UserGroup(
                Key("admin"),
                UserGroup.Name("Administrator"),
                UserGroup.Description("Administrator"),
                UserGroup.Enabled(true),
            )
        ),
        User(
            UserGroup(
                Key("user"),
                UserGroup.Name("User"),
                UserGroup.Description("User"),
                UserGroup.Enabled(true),
            )
        ),
        Disabled(
            UserGroup(
                Key("disabled"),
                UserGroup.Name("Disabled"),
                UserGroup.Description("Disabled"),
                UserGroup.Enabled(false),
            )
        )
    }

    override fun entries(): List<UserGroup> {
        return UserGroups.values().map { it.group }
    }
}
