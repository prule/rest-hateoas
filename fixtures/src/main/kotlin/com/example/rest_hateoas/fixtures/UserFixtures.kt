package com.example.rest_hateoas.fixtures

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User

class UserFixtures(val passwordEncoder: (String) -> String): Fixture<User> {
    enum class Users(val user: User) {
        Fred(
            User(
                Key("fred"),
                User.Username("fred"),
                User.Password("password"),
                User.FirstName("Fred"),
                User.LastName("Doe"),
                User.Enabled(true),
                listOf(UserGroupFixtures.UserGroups.User.group),
            )
        ),
        Boss(
            User(
                Key("boss"),
                User.Username("boss"),
                User.Password("password"),
                User.FirstName("Boss"),
                User.LastName("Doe"),
                User.Enabled(true),
                listOf(UserGroupFixtures.UserGroups.Admin.group)
            )
        )
    }

    override fun entries(): List<User> {
        return Users.values().map {
            // transform password
            it.user.withPassword(User.Password(passwordEncoder(it.user.password.value)))
        }
    }
}
