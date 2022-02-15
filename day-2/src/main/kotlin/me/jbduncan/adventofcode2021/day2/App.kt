package me.jbduncan.adventofcode2021.day2

import java.io.Writer
import java.nio.file.Path
import kotlin.io.path.forEachLine
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8
import me.jbduncan.adventofcode2021.lib.printLine

fun main(args: Array<String>) {
  exitProcess(execute(args, System.out.bufferedWriter(UTF_8), System.err.bufferedWriter(UTF_8)))
}

internal fun execute(args: Array<out Any>, out: Writer, err: Writer): Int {
  if (args.isEmpty()) {
    err.printLine("Usage: day-2 <input-file> [--part-2]")
    return 1
  }
  val inputFile = Path.of(args[0].toString())
  val part2 = args.size >= 2 && args[1] == "--part-2"

  val result =
      if (!part2) {
        getHorizontalPositionAndDepth(inputFile)
      } else {
        getHorizontalPositionAndDepthViaAiming(inputFile)
      }

  out.printLine("Horizontal position: ${result.horizontalPosition}")
  out.printLine("Depth: ${result.depth}")

  return 0
}

private data class HorizontalPositionAndDepth(val horizontalPosition: Int, val depth: Int)

private fun getHorizontalPositionAndDepth(inputFile: Path): HorizontalPositionAndDepth {
  var horizontalPosition = 0
  var depth = 0
  inputFile.forEachLine { instruction ->
    val (direction, distance) = instruction.split(' ', limit = 2)
    when (direction) {
      "forward" -> horizontalPosition += distance.toInt()
      "down" -> depth += distance.toInt()
      "up" -> depth -= distance.toInt()
    }
  }
  return HorizontalPositionAndDepth(horizontalPosition, depth)
}

private fun getHorizontalPositionAndDepthViaAiming(inputFile: Path): HorizontalPositionAndDepth {
  var horizontalPosition = 0
  var depth = 0
  var aim = 0
  inputFile.forEachLine { instruction ->
    val (direction, distance) = instruction.split(' ', limit = 2)
    when (direction) {
      "forward" -> {
        horizontalPosition += distance.toInt()
        depth += distance.toInt() * aim
      }
      "down" -> aim += distance.toInt()
      "up" -> aim -= distance.toInt()
    }
  }
  return HorizontalPositionAndDepth(horizontalPosition, depth)
}
