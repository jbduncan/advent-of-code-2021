package me.jbduncan.adventofcode2021.day1

import io.kotest.core.TestConfiguration
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import kotlin.io.path.toPath

class AppTest :
    BehaviorSpec({
      Given("an empty list of depths") {
        When("running the app") {
          Then("it reports 0 increases") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(emptyInputFile()), out, err)

            out.trim() shouldBe "0"
          }
        }
      }

      Given("everyone's list of depths") {
        When("running the app") {
          Then("it reports 7 increases") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesInputFile()), out, err)

            out.trim() shouldBe "7"
          }
        }

        When("running the app with flag --part-2") {
          Then("it reports 5 increases") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesInputFile(), "--part-2"), out, err)

            out.trim() shouldBe "5"
          }
        }
      }

      Given("my list of depths") {
        When("running the app") {
          Then("it reports 1466 increases") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(myInputFile()), out, err)

            out.trim() shouldBe "1466"
          }
        }

        When("running the app with flag --part-2") {
          Then("it reports 1491 increases") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(myInputFile(), "--part-2"), out, err)

            out.trim() shouldBe "1491"
          }
        }
      }
    })

private fun TestConfiguration.emptyInputFile(): Path {
  return tempfile().toPath()
}

private fun TestConfiguration.everyonesInputFile(): Path {
  return tempfile()
      .apply {
        writeText(
            """
          199
          200
          208
          210
          200
          207
          240
          269
          260
          263
        """.trimIndent())
      }
      .toPath()
}

private fun myInputFile() = resource("/input.txt")

fun resource(resourcePath: String): Path {
  return {}.javaClass //
      .getResource(resourcePath)
      ?.toURI()
      ?.toPath()
      ?: throw IllegalArgumentException(
          "Path '${resourcePath}' doesn't exist in the resources folder. " +
              "Did you forget to prefix the path with a '/'?")
}
