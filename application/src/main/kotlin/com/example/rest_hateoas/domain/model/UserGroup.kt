package com.example.rest_hateoas.domain.model


class UserGroup(
    val id: Long? = null,
    val key: Key,
    var name: String,
    var description: String,
    var enabled: Boolean,
) {
    override fun toString(): String {
        return "UserGroup(id=$id, key=$key, name='$name', description='$description', enabled=$enabled)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserGroup

        if (id != other.id) return false
        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + key.hashCode()
        return result
    }


}