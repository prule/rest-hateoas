package com.example.rest_hateoas.domain.model
import kotlinx.serialization.Serializable


class UserGroup(
    val key: Key,
    var name: Name,
    var description: Description,
    var enabled: Enabled,
) {
    override fun toString(): String {
        return "UserGroup(key=$key, name='$name', description='$description', enabled=$enabled)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserGroup

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode() ?: 0
    }

    @Serializable
    @JvmInline
    value class Name(val value: String)
    @Serializable
    @JvmInline
    value class Description(val value: String)
    @Serializable
    @JvmInline
    value class Enabled(val value: Boolean)

}