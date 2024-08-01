package com.example.rest_hateoas.common

import com.example.rest_hateoas.user.UserGroup
import io.kotest.matchers.collections.shouldContainOnly
import org.junit.jupiter.api.Test

class FieldVisibilityTest {
    @Test
    fun `user should not see admin fields`() {
        val visibility = FieldVisibility(
            mapOf(
                "a" to listOf(UserGroup.Group.ADMIN),
                "b" to listOf(UserGroup.Group.USER)
            )
        )

        val fields = Fields(listOf("a", "b", "c"))
        val result = visibility.of(listOf(UserGroup.Group.USER), fields)
        result.fields shouldContainOnly  listOf("b")
    }
}