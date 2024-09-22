package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.user

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User

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
