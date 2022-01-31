package me.jbduncan.adventofcode2021.day15

import com.google.common.base.Stopwatch
import com.google.mu.util.graph.ShortestPath.shortestPathsFrom
import com.google.mu.util.stream.BiStream.biStream
import java.io.Writer
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8
import org.joda.collect.grid.DenseGrid
import org.joda.collect.grid.Grid
import org.joda.collect.grid.ImmutableGrid

fun main(args: Array<String>) {
  exitProcess(execute(args, System.out.bufferedWriter(UTF_8), System.err.bufferedWriter(UTF_8)))
}

internal fun <T> execute(args: Array<T>, out: Writer, err: Writer): Int {
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
  val safestPath =
    shortestPathsFrom(start) { cell ->
        biStream(grid.neighborsOf(cell)) { neighbor -> neighbor.value.toDouble() }
      }
      .filter { path -> path.to() == end }
      .findFirst()
      .orElseThrow()
  err.printLine(
    "DEBUG: Safest path: calculation time: ${safestPathStopwatch.elapsed().toMillis()}ms"
  )

  val lowestTotalRisk = safestPath.distance().toInt()

  out.printLine(lowestTotalRisk)

  return 0
}

private fun Writer.printLine(obj: Any) {
  appendLine(obj.toString())
  flush()
}

private fun toGrid(lines: List<String>): ImmutableGrid<Int> {
  val result = DenseGrid.create<Int>(lines.size, lines[0].length)
  for (row in (0 until result.rowCount())) {
    for (column in (0 until result.columnCount())) {
      result.put(row, column, lines[row][column].digitToInt())
    }
  }
  return ImmutableGrid.copyOf(result)
}

private fun to5Times5ExpandedGrid(grid: Grid<Int>): ImmutableGrid<Int> {
  val result = DenseGrid.create<Int>(grid.rowCount() * 5, grid.columnCount() * 5)
  for (i in 0..4) {
    for (j in 0..4) {
      for (rowIndex in 0 until grid.rowCount()) {
        for (columnIndex in 0 until grid.columnCount()) {
          val value = grid.get(rowIndex, columnIndex).incBy(i + j)
          result.put(
            (i * grid.rowCount()) + rowIndex,
            (j * grid.columnCount()) + columnIndex,
            value
          )
        }
      }
    }
  }
  return ImmutableGrid.copyOf(result)
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
  val result = mutableSetOf<Grid.Cell<V>>()
  val up = cell.row - 1
  if (up >= 0) {
    result.add(cell(up, cell.column))
  }
  val right = cell.column + 1
  if (right < columnCount()) {
    result.add(cell(cell.row, right))
  }
  val down = cell.row + 1
  if (down < rowCount()) {
    result.add(cell(down, cell.column))
  }
  val left = cell.column - 1
  if (left >= 0) {
    result.add(cell(cell.row, left))
  }
  return result
}
