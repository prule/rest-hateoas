package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person

import com.example.rest_hateoas.adapter.out.persistence.jpa.*
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.common.Loader
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person.PersonFixtures.Persons
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person
import com.example.rest_hateoas.domain.model.PersonAddress
import com.example.rest_hateoas.domain.model.PersonName
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Order(3)
class PersonSampleLoader(val personRepository: PersonJpaRepository): Loader {

    override fun load() {
        // example loading from yaml
        loadData("data/sample/persons.json5").iterator().forEach {
            personRepository.save(PersonMapper.toDomain(it))
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

    private fun loadData(path: String): Iterable<PersonJpaEntity> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<PersonJpaEntity>>(inputStream)
    }
}
