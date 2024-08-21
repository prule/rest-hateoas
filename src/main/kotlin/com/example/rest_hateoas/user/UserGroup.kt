package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*

@Entity
class UserGroup(

    @Basic(optional = false)
    val key: Key,

    @Column(nullable = false) val name: String,
    @Column(nullable = false) val description: String,
    @Column(nullable = false) val enabled: Boolean,
    // Make sure to place the id attribute in last place as it is optional and auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) : AbstractEntity<String?>() {
    enum class Group(val id: String) {
        ADMIN("Admin"),
        USER("User")
    }
}
