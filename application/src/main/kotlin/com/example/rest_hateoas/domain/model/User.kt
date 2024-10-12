package com.example.rest_hateoas.domain.model

import com.example.rest_hateoas.domain.Validator


class User(
    val key: Key,
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var enabled: Boolean,
    var groups: List<UserGroup>,

    val metaData: ModelMetadata = ModelMetadata(),

) {

    fun isValid(validator: Validator) {
        validator.validate("key") {
            key.isValid(validator)
        }
        validator.check(username.isNotBlank()) {
            "Username cannot be blank"
        }
        validator.check(password.isNotBlank()) {
            "Password cannot be blank"
        }
        validator.check(firstName.isNotBlank()) {
            "First name cannot be blank"
        }
        validator.check(lastName.isNotBlank()) {
            "Last name cannot be blank"
        }
    }

    fun addGroup(group: UserGroup) {
        check(!hasGroup(group)) {
            "User already has group ${group.key}"
        }
        groups = groups + group
    }

    fun removeGroup(group: UserGroup) {
        check(hasGroup(group)) {
            "User does not have group ${group.key}"
        }
        groups = groups.filter { it != group }
    }

    fun hasGroup(group: UserGroup): Boolean {
        return groups.contains(group)
    }

    override fun toString(): String {
        return "User(key=$key, username='$username', password='$password', firstName='$firstName', lastName='$lastName', enabled=$enabled, groups=$groups)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result
        return result
    }

    fun withPassword(password: String): User {
        return User(key, username, password, firstName, lastName, enabled, groups, metaData)
    }

}