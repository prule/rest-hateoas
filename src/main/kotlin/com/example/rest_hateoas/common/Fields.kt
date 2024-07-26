package com.example.rest_hateoas.common

import com.google.common.base.Splitter
import java.util.function.BiConsumer
import java.util.stream.Collectors

class Fields {
    private val fields: List<String>

    constructor(fields: String?) {
        this.fields = Splitter.on(",").splitToList(fields ?: ALL)
    }

    private constructor(fields: List<String>) {
        this.fields = fields
    }

    fun set(fieldName: String, setFunction: Runnable): Fields {
        if (hasField(fieldName)) {
            setFunction.run()
        }
        return this
    }

    fun <T> setNested(fieldName: String, model: T?, setFunction: BiConsumer<T, Fields?>): Fields {
        if (model != null) {
            if (hasNestedField(fieldName)) {
                setFunction.accept(model, this.from(fieldName))
            }
        }
        return this
    }

    fun hasField(fieldName: String): Boolean {
        return (!isNested(fieldName) && fields.contains(ALL)) || fields.contains(fieldName)
    }

    fun hasNestedField(fieldName: String): Boolean {
        return fields.stream().anyMatch { name: String -> name.startsWith(fieldName + NESTED_SEPARATOR) }
    }

    private fun isNested(fieldName: String): Boolean {
        return fieldName.contains(NESTED_SEPARATOR)
    }

    fun from(path: String): Fields {
        val prefix = path + NESTED_SEPARATOR
        val subset = fields.stream()
            .filter { name: String -> name.startsWith(prefix) }
            .map { name: String -> name.substring(prefix.length) }
            .collect(Collectors.toList())
        return Fields(subset)
    }

    companion object {
        private const val ALL = "*"
        private const val NESTED_SEPARATOR = "."

        fun all(): Fields {
            return Fields(ALL)
        }
    }
}
