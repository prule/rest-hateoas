package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person

import com.example.rest_hateoas.adapter.out.persistence.jpa.person.PersonJpaRepository
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.common.Loader
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.person.PersonFixtures.Persons
import com.example.rest_hateoas.application.port.`in`.person.PersonCreateUseCase
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.Person
import com.example.rest_hateoas.domain.model.PersonAddress
import com.example.rest_hateoas.domain.model.PersonName
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.ZonedDateTime

// TODO convert this to use domain objects

@Component
@Order(3)
class PersonSampleLoader(val personRepository: PersonJpaRepository, val personCreateUseCase: PersonCreateUseCase): Loader {

    override fun load() {
        // example loading from yaml

//        loadData("data/sample/persons.json5").iterator().forEach {
//            personCreateUseCase.create(it)
//            personRepository.save(PersonJpaMapper.toDomain(it))
//        }

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
//        return Json5.decodeFromStream<List<Person>>(inputStream)
//        return Gson().fromJson(inputStream, Array<Person>::class.java).toList()
//        return Gson().fromJson(inputStream, TokenType)

//        val gson = Gson()
        val gson =
            GsonBuilder().registerTypeAdapter(ZonedDateTime::class.java, object : JsonDeserializer<ZonedDateTime?> {
                @Throws(JsonParseException::class)
                override fun deserialize(
                    json: JsonElement,
                    type: Type?,
                    jsonDeserializationContext: JsonDeserializationContext?
                ): ZonedDateTime {
                    return ZonedDateTime.parse(json.asJsonPrimitive.asString)
                }
            }).create()

        val itemType = object : TypeToken<List<Person>>() {}.type
        return gson.fromJson<List<Person>>(InputStreamReader(inputStream), itemType)
    }
}
