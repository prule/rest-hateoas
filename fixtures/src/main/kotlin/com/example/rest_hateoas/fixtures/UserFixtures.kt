package com.example.rest_hateoas.fixtures

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User

class UserFixtures(val passwordEncoder: (String) -> String): Fixture<User> {
    enum class Users(val user: User) {
        Fred(
            User(
                Key("fred"),
                "fred",
                "password",
                "Fred",
                "Doe",
                true,
                listOf(UserGroupFixtures.UserGroups.User.group),
            )
        ),
        Boss(
            User(
                Key("boss"),
                "boss",
                "password",
                "Boss",
                "Doe",
                true,
                listOf(UserGroupFixtures.UserGroups.Admin.group)
            )
        )
    }

    override fun entries(): List<User> {
        return Users.values().map {
            // transform password
            it.user.withPassword(passwordEncoder(it.user.password))
        }
    }
}
