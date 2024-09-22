package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Table(name = "user_group")
class UserGroupJpaEntity(

    @Basic(optional = false)
    val key: KeyJpaEntity,

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
