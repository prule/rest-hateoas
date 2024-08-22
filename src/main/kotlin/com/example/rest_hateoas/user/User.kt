package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Serializable
@Entity(name = "usr") // name of table is changed to avoid naming conflicts with database
class User(

    @Basic(optional = false)
    val key: Key,

    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var enabled: Boolean,
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = UserGroup::class)
    var groups: MutableSet<UserGroup>,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
) : AbstractEntity<String?>() {

    fun hasRole(role: String): Boolean {
        return groups.find { it.name == role } != null
    }

}
