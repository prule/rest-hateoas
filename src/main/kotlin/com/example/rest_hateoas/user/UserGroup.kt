package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*

@Entity
class UserGroup : AbstractEntity<String?> {
    enum class Group(val id: String) {
        ADMIN("Admin"),
        USER("User")
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    @Basic
    private var name: String? = null

    @Basic
    private var description: String? = null

    @Basic
    private var enabled = false

    internal constructor() : super()

    constructor(key: Key?, name: String?, description: String?, enabled: Boolean) {
        this.name = name
        this.description = description
        this.enabled = enabled
    }
}
