package com.example.rest_hateoas.adapter.jsonassert

import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher

class Customizations {
    companion object {
        fun timestamp(path: String): Customization {
            return Customization(
                path,
                RegularExpressionValueMatcher<Any>("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+[+\\-]\\d{2}:\\d{2}$")
            )
        }

        fun conflict(path: String): Customization {
            return Customization(
                path,
                RegularExpressionValueMatcher<Any>("^Row was updated or deleted by another transaction.*")
            )
        }

        fun key(path: String): Customization {
            return Customization(
                path,
                RegularExpressionValueMatcher<Any>("^\\w+$")
            )
        }

        fun link(path: String, prefix: String): Customization {
            return Customization(
                path,
                RegularExpressionValueMatcher<Any>("^$prefix\\w+$")
            )
        }
    }
}