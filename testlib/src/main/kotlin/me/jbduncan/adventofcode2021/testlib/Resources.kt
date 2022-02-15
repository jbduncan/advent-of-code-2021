package me.jbduncan.adventofcode2021.testlib

import java.nio.file.Path
import kotlin.io.path.toPath

fun resource(resourcePath: String): Path {
  return {}.javaClass //
      .getResource(resourcePath)
      ?.toURI()
      ?.toPath()
      ?: throw IllegalArgumentException(
          "Path '${resourcePath}' doesn't exist in the resources folder. " +
              "Did you forget to prefix the path with a '/'?")
}
