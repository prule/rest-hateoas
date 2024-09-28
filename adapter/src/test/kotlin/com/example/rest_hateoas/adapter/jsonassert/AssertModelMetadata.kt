package com.example.rest_hateoas.adapter.jsonassert

import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.comparator.CustomComparator

class AssertModelMetadata {
    companion object {
        fun assert(expectedResponseBody: String, actualResponseBody: String, vararg customizations: Customization) {
            JSONAssert.assertEquals(
                expectedResponseBody,
                actualResponseBody,
                CustomComparator(
                    JSONCompareMode.STRICT,
                    Customizations.timestampZ("metadata.createdDate"),
                    Customizations.timestampZ("metadata.lastModifiedDate"),
                    *customizations
                )
            )
        }
    }
}