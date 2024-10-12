package com.example.rest_hateoas.fixtures

interface Fixture<T> {
    fun entries(): List<T>
}