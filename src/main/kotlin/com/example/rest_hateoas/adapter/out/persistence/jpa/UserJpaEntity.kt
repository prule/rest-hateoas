package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.common.AbstractEntity
import com.example.rest_hateoas.common.Key
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Entity(name = "usr") // name of table is changed to avoid naming conflicts with database
class UserJpaEntity(

    @Basic(optional = false)
    val key: Key,

    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = UserGroupJpaEntity::class, cascade = [CascadeType.ALL])
    val groups: List<UserGroupJpaEntity>,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)
