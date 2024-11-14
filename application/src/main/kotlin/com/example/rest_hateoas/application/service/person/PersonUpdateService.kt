package com.example.rest_hateoas.application.service.person

import com.example.rest_hateoas.application.port.`in`.person.PersonUpdateUseCase
import com.example.rest_hateoas.application.port.`in`.person.UpdatePersonCommand
import com.example.rest_hateoas.application.port.`in`.user.UpdateUserCommand
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.domain.model.Person

class PersonUpdateService(val personRepository: PersonRepository): PersonUpdateUseCase {
    /**
     * Here, we load the existing person and update it with the new values.
     * Then we save the updated person.
     */
    override fun update(command: UpdatePersonCommand) {
        val existing = command.person

        // logic about what values can be updated - could be based on the user's role
        existing.name = command.newValues.name
        existing.address = command.newValues.address
        existing.dateOfBirth = command.newValues.dateOfBirth

        // copy across the version the updated value was based on so that we can detect if it was updated by another user
        existing.metaData.version = command.newValues.version

        personRepository.save(existing)
    }
}