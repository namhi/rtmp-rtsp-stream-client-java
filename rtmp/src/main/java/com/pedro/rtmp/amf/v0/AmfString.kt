package com.pedro.rtmp.amf.v0

import com.pedro.rtmp.amf.AmfData
import com.pedro.rtmp.utils.readUInt16
import com.pedro.rtmp.utils.readUntil
import com.pedro.rtmp.utils.writeUInt16
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.jvm.Throws

/**
 * Created by pedro on 8/04/21.
 *
 * A string encoded in ASCII where 2 first bytes indicate string size
 */
class AmfString(var value: String = ""): AmfData() {

  private var bodySize: Int = value.length

  @Throws(IOException::class)
  override fun readBody(input: InputStream) {
    //read value size as UInt16
    bodySize = input.readUInt16()
    //read value in ASCII
    val bytes = ByteArray(bodySize)
    input.readUntil(bytes)
    value = String(bytes, Charsets.US_ASCII)
  }

  @Throws(IOException::class)
  override fun writeBody(output: OutputStream) {
    val bytes = value.toByteArray(Charsets.US_ASCII)
    //write value size as UInt16
    output.writeUInt16(bodySize)
    //write value bytes in ASCII
    output.write(bytes)
  }

  override fun getType(): AmfType = AmfType.STRING

  override fun getSize(): Int = bodySize
}