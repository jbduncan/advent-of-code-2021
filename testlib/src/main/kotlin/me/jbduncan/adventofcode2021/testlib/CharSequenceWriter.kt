package me.jbduncan.adventofcode2021.testlib

import java.io.StringWriter
import java.io.Writer

class CharSequenceWriter : Writer(), CharSequence {

  private val delegateWriter = StringWriter()

  override fun close() {
    delegateWriter.close()
  }

  override fun flush() {
    delegateWriter.flush()
  }

  override fun write(cbuf: CharArray, off: Int, len: Int) {
    delegateWriter.write(cbuf, off, len)
  }

  override val length: Int
    get() = delegateWriter.buffer.length

  override fun get(index: Int): Char {
    return delegateWriter.buffer[index]
  }

  override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
    return delegateWriter.buffer.subSequence(startIndex, endIndex)
  }
}
