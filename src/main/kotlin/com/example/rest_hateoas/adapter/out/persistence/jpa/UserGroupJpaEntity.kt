package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Entity
class UserGroupJpaEntity(

    @Basic(optional = false)
    val key: Key,

    @Column(nullable = false) var name: String,
    @Column(nullable = false) var description: String,
    @Column(nullable = false) var enabled: Boolean,
    // Make sure to place the id attribute in last place as it is optional and auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) {
    enum class Group(val id: String) {
        ADMIN("Admin"),
        USER("User")
    }
}
