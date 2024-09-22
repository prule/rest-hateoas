package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.domain.model.Key

class KeyMapper {
    companion object {
        fun toDomain(value: KeyJpaEntity): Key {
            return Key(
                value.key,
            )
        }

        fun toJpaEntity(value: Key): KeyJpaEntity {
            return KeyJpaEntity(
                value.key,
            )
        }
    }
}