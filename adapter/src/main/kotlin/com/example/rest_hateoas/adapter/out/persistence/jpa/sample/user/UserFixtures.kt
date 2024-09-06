package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.user

import com.example.rest_hateoas.application.domain.model.User
import com.example.rest_hateoas.application.domain.model.Key

class UserFixtures {
    enum class Users(val user: User) {
        Fred(
            User(
                Key("fred"),
                "fred",
                "password",
                "Fred",
                "Doe",
                true,
                listOf()
            )
        )
    }
}
