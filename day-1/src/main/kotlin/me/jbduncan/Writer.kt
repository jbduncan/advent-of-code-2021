package me.jbduncan

import java.io.Writer

fun Writer.printLine(obj: Any) {
  appendLine(obj.toString())
  flush()
}
