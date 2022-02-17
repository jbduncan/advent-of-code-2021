package me.jbduncan.adventofcode2021.day12

import com.google.common.graph.GraphBuilder
import com.google.common.graph.ImmutableGraph
import java.io.Writer
import java.nio.file.Path
import kotlin.io.path.forEachLine
import kotlin.system.exitProcess
import me.jbduncan.adventofcode2021.lib.printLine

fun main(args: Array<String>) {
  exitProcess(
      execute(
          args,
          System.out.bufferedWriter(Charsets.UTF_8),
          System.err.bufferedWriter(Charsets.UTF_8)))
}

internal fun execute(args: Array<out Any>, out: Writer, err: Writer): Int {
  if (args.isEmpty()) {
    err.printLine("Usage: day-2 <input-file> [--part-2]")
    return 1
  }
  val inputFile = Path.of(args[0].toString())
  val part2 = args.size >= 2 && args[1] == "--part-2"

  val caveSystemMap =
      GraphBuilder.undirected()
          .immutable<String>()
          .also { builder ->
            inputFile.forEachLine { corridor ->
              val (caveA, caveB) = corridor.split('-', limit = 2)
              builder.putEdge(caveA, caveB)
            }
          }
          .build()

  out.printLine(countPaths(caveSystemMap, "start", "end"))

  return 0
}

private fun countPaths(
    caveSystemMap: ImmutableGraph<String>,
    startCave: String,
    endCave: String
): Int {
  return countPathsRecursive(caveSystemMap, startCave, endCave, setOf(startCave))
}

private fun countPathsRecursive(
    caveSystemMap: ImmutableGraph<String>,
    currentCave: String,
    endCave: String,
    visitedCaves: Set<String>
): Int {
  var result = 0
  for (adjacentCave in caveSystemMap.adjacentNodes(currentCave)) {
    if (adjacentCave == endCave) {
      result++
      continue
    }

    if (isSmallCave(adjacentCave) && adjacentCave in visitedCaves) {
      continue
    }

    result += countPathsRecursive(caveSystemMap, adjacentCave, endCave, visitedCaves + adjacentCave)
  }
  return result
}

private fun isSmallCave(cave: String): Boolean {
  return cave.all(Char::isLowerCase)
}
