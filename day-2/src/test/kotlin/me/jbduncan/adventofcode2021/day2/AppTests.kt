package me.jbduncan.adventofcode2021.day2

import io.kotest.assertions.assertSoftly
import io.kotest.core.TestConfiguration
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import me.jbduncan.adventofcode2021.testlib.CharSequenceWriter
import me.jbduncan.adventofcode2021.testlib.resource

class AppTests :
    BehaviorSpec({
      Given("everyone's planned course") {
        When("running the app") {
          Then("it reports a horizontal position of 15 and a depth of 10") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            val exitCode = execute(arrayOf(everyonesInputFile()), out, err)

            assertSoftly {
              exitCode.shouldBeZero()
              out.trim().lines() shouldBe listOf("Horizontal position: 15", "Depth: 10")
            }
          }
        }

        When("running the app with flag --part-2") {
          Then("it reports a horizontal position of 15 and a depth of 60") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            val exitCode = execute(arrayOf(everyonesInputFile(), "--part-2"), out, err)

            assertSoftly {
              exitCode.shouldBeZero()
              out.trim().lines() shouldBe listOf("Horizontal position: 15", "Depth: 60")
            }
          }
        }
      }

      Given("my planned course") {
        When("running the app") {
          Then("it reports a horizontal position of 1815 and a depth of 908") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            val exitCode = execute(arrayOf(myInputFile()), out, err)

            assertSoftly {
              exitCode.shouldBeZero()
              out.trim().lines() shouldBe listOf("Horizontal position: 1815", "Depth: 908")
            }
          }
        }

        When("running the app with flag --part-2") {
          Then("it reports a horizontal position of 1815 and a depth of 969597") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            val exitCode = execute(arrayOf(myInputFile(), "--part-2"), out, err)

            assertSoftly {
              exitCode.shouldBeZero()
              out.trim().lines() shouldBe listOf("Horizontal position: 1815", "Depth: 969597")
            }
          }
        }
      }
    })

private fun TestConfiguration.everyonesInputFile(): Path {
  return tempfile()
      .apply {
        writeText(
            """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
            """.trimIndent())
      }
      .toPath()
}

private fun myInputFile() = resource("/input.txt")
