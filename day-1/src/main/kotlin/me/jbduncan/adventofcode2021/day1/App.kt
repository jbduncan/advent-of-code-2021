package me.jbduncan.adventofcode2021.day1

import java.io.Writer
import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8

fun main(args: Array<String>) {
  exitProcess(execute(args, System.out.bufferedWriter(UTF_8), System.err.bufferedWriter(UTF_8)))
}

internal fun execute(args: Array<out Any>, out: Writer, err: Writer): Int {
  if (args.isEmpty()) {
    err.printLine("Usage: day-1 <input-file> [--part-2]")
    return 1
  }
  val inputFile = Path.of(args[0].toString())
  val part2 = args.size >= 2 && args[1].toString() == "--part-2"

  val result =
      inputFile.useLines(UTF_8) { depths ->
        depths
            .map(String::toInt)
            .windowed(size = if (part2) 3 else 1, transform = List<Int>::sum)
            .zipWithNext { previous, current -> if (previous < current) 1 else 0 }
            .sum()
      }

  out.printLine(result)

  return 0
}

private fun Writer.printLine(obj: Any) {
  appendLine(obj.toString())
  flush()
}
