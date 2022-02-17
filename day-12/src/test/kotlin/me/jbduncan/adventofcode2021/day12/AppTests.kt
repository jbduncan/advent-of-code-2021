package me.jbduncan.adventofcode2021.day12

import io.kotest.core.TestConfiguration
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import me.jbduncan.adventofcode2021.testlib.CharSequenceWriter
import me.jbduncan.adventofcode2021.testlib.resource

class AppTests :
    BehaviorSpec({
      Given("everyone's first cave system map") {
        When("running the app") {
          Then("it reports 10 paths") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesFirstInputFile()), out, err)

            println(err.trim())
            out.trim() shouldBe "10"
          }
        }
      }

      Given("everyone's second cave system map") {
        When("running the app") {
          Then("it reports 19 paths") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesSecondInputFile()), out, err)

            println(err.trim())
            out.trim() shouldBe "19"
          }
        }
      }

      Given("everyone's third cave system map") {
        When("running the app") {
          Then("it reports 226 paths") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(everyonesThirdInputFile()), out, err)

            println(err.trim())
            out.trim() shouldBe "226"
          }
        }
      }

      Given("my cave system map") {
        When("running the app") {
          Then("it reports 3369 paths") {
            val out = CharSequenceWriter()
            val err = CharSequenceWriter()

            execute(arrayOf(myInputFile()), out, err)

            println(err.trim())
            out.trim() shouldBe "3369"
          }
        }
      }
    })

private fun TestConfiguration.everyonesFirstInputFile(): Path {
  return tempfile()
      .apply {
        writeText(
            """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
            """.trimIndent())
      }
      .toPath()
}

private fun TestConfiguration.everyonesSecondInputFile(): Path {
  return tempfile()
      .apply {
        writeText(
            """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
            """.trimIndent())
      }
      .toPath()
}

private fun TestConfiguration.everyonesThirdInputFile(): Path {
  return tempfile()
      .apply {
        writeText(
            """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
            """.trimIndent())
      }
      .toPath()
}

private fun myInputFile() = resource("/input.txt")
