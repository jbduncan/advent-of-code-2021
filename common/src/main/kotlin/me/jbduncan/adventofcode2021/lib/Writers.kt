package me.jbduncan.adventofcode2021.lib

import java.io.Writer

fun Writer.printLine(obj: Any) {
  appendLine(obj.toString())
  flush()
}
