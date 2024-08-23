package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.Key
import java.time.LocalDate

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
                mutableSetOf()
            )
        )
    }
}
