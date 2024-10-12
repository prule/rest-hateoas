package com.example.rest_hateoas.fixtures

import com.example.rest_hateoas.application.port.out.persistence.Repository
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.slf4j.LoggerFactory

class SampleLoader<T>(
    val repository: Repository<T, *>,
    val fixtures: Fixture<T>
) {
    protected val logger = LoggerFactory.getLogger(javaClass)

    fun loadFixtures() {
        // fixture driven
        for (obj in fixtures.entries()) {
            repository.save(obj)
        }
    }

    fun loadFromJson5(path: String) {
        // load object graph
        val objects = loadData(path)
        // create or update accordingly
        for (obj in objects) {
            repository.save(obj)
        }

    }

    private fun loadData(path: String): Iterable<T> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
        if (inputStream != null) {
            return Json5.decodeFromStream<List<T>>(inputStream)
        } else {
            logger.info("Skipping - No data found: $path")
            return emptyList()
        }
    }
}