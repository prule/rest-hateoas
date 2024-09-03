package com.example.rest_hateoas.adapter.out.persistence.jpa

import jakarta.persistence.*

@Entity(name = "usr") // name of table is changed to avoid naming conflicts with database
class UserJpaEntity(

    @Basic(optional = false)
    val key: KeyJpaEntity,

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
