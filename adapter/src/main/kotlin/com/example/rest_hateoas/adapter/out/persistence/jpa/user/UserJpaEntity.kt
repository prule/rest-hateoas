package com.example.rest_hateoas.adapter.out.persistence.jpa.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.KeyJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.ModelMetadataJpaEntity
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Serializable
@Entity(name = "usr") // name of table is changed to avoid naming conflicts with database
@EntityListeners(AuditingEntityListener::class)
class UserJpaEntity(

    @Basic(optional = false)
    val key: KeyJpaEntity,

    val username: String,
    var password: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    @ManyToMany(fetch = FetchType.EAGER)
    val groups: List<UserGroupJpaEntity>,

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Basic
    @Version
    var version: Long = 0,

    @Embedded
    var metadata: ModelMetadataJpaEntity = ModelMetadataJpaEntity()

)
