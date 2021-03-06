package me.jbduncan.adventofcode2021.day15

import com.google.common.base.Stopwatch
import com.google.mu.util.graph.ShortestPath.shortestPathsFrom
import com.google.mu.util.stream.BiStream.biStream
import java.io.Writer
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.streams.asSequence
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8
import me.jbduncan.adventofcode2021.lib.printLine
import org.joda.collect.grid.DenseGrid
import org.joda.collect.grid.Grid
import org.joda.collect.grid.ImmutableGrid

fun main(args: Array<String>) {
  exitProcess(execute(args, System.out.bufferedWriter(UTF_8), System.err.bufferedWriter(UTF_8)))
}

internal fun execute(args: Array<out Any>, out: Writer, err: Writer): Int {
  if (args.isEmpty()) {
    err.printLine("Usage: day-1 <input-file> [--part-2]")
    return 1
  }
  val inputFile = Path.of(args[0].toString())
  val part2 = args.size >= 2 && args[1] == "--part-2"

  val grid =
      if (!part2) {
        toGrid(inputFile.readLines(UTF_8))
      } else {
        to5Times5ExpandedGrid(toGrid(inputFile.readLines(UTF_8)))
      }

  val start = grid.cell(0, 0)
  val end = grid.cell(grid.rowCount() - 1, grid.columnCount() - 1)

  val safestPathStopwatch = Stopwatch.createStarted()
  // @spotless:off
  val safestPath =
      shortestPathsFrom(start) { cell ->
            biStream(grid.neighborsOf(cell)) { neighbor -> neighbor.value.toDouble() }
          }
          .asSequence()
          .first { path -> path.to() == end }
  // @spotless:on
  err.printLine(
      "DEBUG: Safest path: calculation time: ${safestPathStopwatch.elapsed().toMillis()}ms")

  val lowestTotalRisk = safestPath.distance().toInt()
  out.printLine(lowestTotalRisk)

  return 0
}

private fun toGrid(lines: List<String>): ImmutableGrid<Int> {
  val result = DenseGrid.create<Int>(lines.size, lines[0].length)
  result.forEach { row, column -> result.put(row, column, lines[row][column].digitToInt()) }
  return ImmutableGrid.copyOf(result)
}

private fun to5Times5ExpandedGrid(grid: Grid<Int>): ImmutableGrid<Int> {
  val result = DenseGrid.create<Int>(grid.rowCount() * 5, grid.columnCount() * 5)
  repeat(5) { i ->
    repeat(5) { j ->
      grid.forEach { row, column ->
        val value = grid.get(row, column).incBy(i + j)
        result.put((i * grid.rowCount()) + row, (j * grid.columnCount()) + column, value)
      }
    }
  }
  return ImmutableGrid.copyOf(result)
}

private inline fun <V> Grid<V>.forEach(action: (row: Int, column: Int) -> Unit) {
  repeat(rowCount()) { row ->
    repeat(columnCount()) { column -> //
      action(row, column)
    }
  }
}

private fun Int.incBy(value: Int): Int {
  var result = this
  for (i in 1..value) {
    if (result >= 9) {
      result = 1
    } else {
      result += 1
    }
  }
  return result
}

private fun <V> Grid<V>.neighborsOf(cell: Grid.Cell<V>): Set<Grid.Cell<V>> {
  return buildSet {
    val up = cell.row - 1
    if (up >= 0) {
      add(cell(up, cell.column))
    }
    val right = cell.column + 1
    if (right < columnCount()) {
      add(cell(cell.row, right))
    }
    val down = cell.row + 1
    if (down < rowCount()) {
      add(cell(down, cell.column))
    }
    val left = cell.column - 1
    if (left >= 0) {
      add(cell(cell.row, left))
    }
  }
}
