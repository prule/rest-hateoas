package com.example.rest_hateoas.support

import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.nodes.Node
import org.yaml.snakeyaml.nodes.NodeId
import org.yaml.snakeyaml.nodes.ScalarNode
import java.time.LocalDate

class LocalDateYamlConstructor<T>(loadingConfig: LoaderOptions) : Constructor(loadingConfig) {
    init {
        yamlClassConstructors[NodeId.scalar] = LocalDateConstructor()
    }

    private inner class LocalDateConstructor : ConstructScalar() {
        override fun construct(node: Node): Any {
            return if (node.type == LocalDate::class.java) LocalDate.parse((node as ScalarNode).value)
            else super.construct(node)
        }
    }
}