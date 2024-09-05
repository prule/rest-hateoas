package com.example.rest_hateoas.application.domain.model

import java.util.*

class Key(
    var key: String = (UUID.randomUUID().toString()).replace("-", "")
) {
    override fun toString(): String {
        return "Key(key='$key')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Key

        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

}


