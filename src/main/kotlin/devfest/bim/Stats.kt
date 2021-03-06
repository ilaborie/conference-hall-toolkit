package devfest.bim

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required

object Stats : CliktCommand(name = "stats", help = "Just some stats") {

    private val eventId: String by option("-e", "--event", help = "the event Id").required()
    private val apiKey: String by option("-k", "--api-key", help = "the api key").required()

    override fun run() {
        with(Events(eventId, apiKey)) {

            val talksCount = event.talks.count()
            println("Talks: $talksCount")

            println()
            println("Par format")
            event.talks
                .groupBy { it.format() }
                .mapValues { (_, lst) -> lst.size }
                .toList()
                .sortedBy { -it.second }
                .forEach { (format, count) -> println("  $format: $count") }

            println()
            println("Par categorie")
            event.talks
                .groupBy { it.category() }
                .mapValues { (_, lst) -> lst.size }
                .toList()
                .sortedBy { -it.second }
                .forEach { (category, count) -> println("  $category: $count") }
        }
    }

}