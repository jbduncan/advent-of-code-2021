package me.jbduncan

import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8

fun main(args: Array<String>) {
  exitProcess(execute(args, System.out.bufferedWriter(UTF_8), System.err.bufferedWriter(UTF_8)))
}

fun <T> execute(args: Array<T>, out: Writer, err: Writer): Int {
  if (args.isEmpty()) {
    err.printLine("Usage: day-1 <input-file> [--part-2]")
    return 1
  }
  val inputFile = Path.of(args[0].toString())
  val part2 = args.any { it.toString() == "--part-2" }

  val result =
    Files.lines(inputFile, UTF_8) //
      .use { depths ->
        depths
          .asSequence()
          .map(String::toInt)
          .windowed(size = if (part2) 3 else 1, transform = List<Int>::sum)
          .zipWithNext { previous, current -> if (previous < current) 1 else 0 }
          .sum()
      }

  out.printLine(result)

  return 0
}
