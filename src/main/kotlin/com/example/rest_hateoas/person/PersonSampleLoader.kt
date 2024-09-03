package com.example.rest_hateoas.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.domain.model.PersonAddress
import com.example.rest_hateoas.application.domain.model.PersonName
import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.common.Loader
import com.example.rest_hateoas.person.PersonFixtures.Persons
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Order(3)
class PersonSampleLoader(val personRepository: PersonRepository): Loader {

    override fun load() {
        // example loading from yaml
        loadData("data/sample/persons.json5").iterator().forEach {
            personRepository.save(it)
        }

        // fixture driven
        for (fixture in Persons.entries.toTypedArray()) {
            personRepository.save(fixture.person)
        }

        // programmatic creation
        for (i in 0..9) {
            personRepository.save(generatePerson(i))
        }
    }

    private fun generatePerson(i: Int): Person {
        val key = "person$i"
        return Person(
            Key(key),
            PersonName("$key.firstName", "$key.lastName", "$key.otherNames"),
            PersonAddress("line1", "line2", "city", "state", "country", "postcode"),
            LocalDate.now()
        )
    }

    private fun loadData(path: String): Iterable<Person> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<Person>>(inputStream)
    }
}
