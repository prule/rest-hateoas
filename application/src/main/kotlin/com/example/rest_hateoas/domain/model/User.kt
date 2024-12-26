package com.example.rest_hateoas.domain.model

import com.example.rest_hateoas.domain.Validator
import kotlinx.serialization.Serializable


class User(
    val key: Key,
    var username: Username,
    var password: Password,
    var firstName: FirstName,
    var lastName: LastName,
    var enabled: Enabled,
    var groups: List<UserGroup>,

    val metaData: ModelMetadata = ModelMetadata(),

) {

    fun hasPermission(action: String, resource: Any): Boolean {
        return groups.any { it.hasPermission(action, resource) }
    }

    fun isValid(validator: Validator) {
        validator.validate("key") {
            key.isValid(validator)
        }
        validator.check(username.value.isNotBlank()) {
            "Username cannot be blank"
        }
        validator.check(password.value.isNotBlank()) {
            "Password cannot be blank"
        }
        validator.check(firstName.value.isNotBlank()) {
            "First name cannot be blank"
        }
        validator.check(lastName.value.isNotBlank()) {
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

    fun withPassword(password: Password): User {
        return User(key, username, password, firstName, lastName, enabled, groups, metaData)
    }

    @Serializable
    @JvmInline
    value class Username(val value: String)

    @Serializable
    @JvmInline
    value class Password(val value: String)

    @Serializable
    @JvmInline
    value class FirstName(val value: String)

    @Serializable
    @JvmInline
    value class LastName(val value: String)

    @Serializable
    @JvmInline
    value class Enabled(val value: Boolean)


}