package me.jbduncan.adventofcode2021.day15

import io.kotest.core.TestConfiguration
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import me.jbduncan.adventofcode2021.testlib.CharSequenceWriter
import me.jbduncan.adventofcode2021.testlib.resource

class AppTests :
    BehaviorSpec({
      Given("everyone's matrix of risk levels in the cavern") {
        When("running the app") {
          Then("it reports 40 as the minimum total risk") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesInputFile()), out, err)

            println(err.trim())
            out.trim() shouldBe "40"
          }
        }

        When("running the app with flag --part-2") {
          Then("it reports 315 as the minimum total risk") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesInputFile(), "--part-2"), out, err)

            println(err.trim())
            out.trim() shouldBe "315"
          }
        }
      }

      Given("my matrix of risk levels in the cavern") {
        When("running the app") {
          Then("it reports 656 as the minimum total risk") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(myInputFile()), out, err)

            println(err.trim())
            out.trim() shouldBe "656"
          }
        }

        When("running the app with flag --part-2") {
          Then("it reports 2979 as the minimum total risk") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(myInputFile(), "--part-2"), out, err)

            println(err.trim())
            out.trim() shouldBe "2979"
          }
        }
      }
    })

private fun TestConfiguration.everyonesInputFile(): Path {
  return tempfile()
      .apply {
        writeText(
            """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
            """.trimIndent())
      }
      .toPath()
}

private fun myInputFile() = resource("/input.txt")
