package com.example.rest_hateoas.data

import com.example.rest_hateoas.common.Address
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.data.Fixtures.Persons
import com.example.rest_hateoas.person.Person
import com.example.rest_hateoas.person.PersonName
import com.example.rest_hateoas.person.PersonRepository
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.function.Function

@Component
class SampleLoader(val personRepository: PersonRepository) {

    fun load() {
        // example loading from yaml

        createOrUpdate("data/sample/persons.json5") { obj: Any -> createOrUpdatePerson(obj as Person) }

        // fixture driven
        for (fixture in Persons.entries.toTypedArray()) {
            createOrUpdatePerson(fixture.person)
        }

        // programmatic creation
        for (i in 0..9) {
            createOrUpdatePerson(generatePerson(i))
        }
    }

    private fun generatePerson(i: Int): Person {
        val key = "person$i"
        return Person(
            Key(key),
            PersonName("$key.firstName", "$key.lastName", "$key.otherNames"),
            Address("line1", "line2", "city", "state", "country", "postcode"),
            LocalDate.now()
        )
    }

    private fun createOrUpdate(path: String, runnable: Function<Person, Person>) {
        // load object graph
        val objects = loadData(path)
        // create or update accordingly
        for (obj in objects) {
            runnable.apply(obj)
        }
    }

    private fun createOrUpdatePerson(newPerson: Person): Person {
        val person = personRepository.findByKey(newPerson.key)!!.orElse(newPerson)

        // copy fields we wish to update
        person.name = person.name
        person.address = person.address
        person.dateOfBirth = person.dateOfBirth
        personRepository.save(person)

        return person
    }

    private fun loadData(path: String): Iterable<Person> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<Person>>(inputStream)
    }
}
