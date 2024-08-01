package com.example.rest_hateoas.common

import com.example.rest_hateoas.user.UserGroup

class FieldVisibility(val groupsPerField: Map<String, List<UserGroup.Group>>) {

    /**
     * Returns a sanitised version of the requested fields based on the fields allowed for the given groups.
     */
    fun of(groups: List<UserGroup.Group>, fields: Fields): Fields {
        return Fields(
            fields.fields.filter { field -> groupsPerField[field]?.intersect(groups)?.isNotEmpty() == true }
        )
    }
}