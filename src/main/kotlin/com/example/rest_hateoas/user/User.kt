package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.AbstractEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity(name = "usr") // name of table is changed to avoid naming conflicts with database
class User internal constructor() : AbstractEntity<String?>() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    @NotNull
    @Basic
    private val username: String? = null

    @NotNull
    @Basic
    private val password: String? = null

    @NotNull
    @Basic
    private val firstName: String? = null

    @NotNull
    @Basic
    private val lastName: String? = null

    @NotNull
    @Basic
    private val enabled = false

    @ManyToMany(fetch = FetchType.EAGER)
    private val groups: Set<UserGroup>? = null

    companion object {
        fun hasRole(role: String?): Boolean {
            return false
        }
    }
}
