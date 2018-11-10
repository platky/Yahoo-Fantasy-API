package utils

import java.io.File
import java.io.FileInputStream
import java.net.URLDecoder
import java.util.*


object PropertiesReader {
    private val properties = Properties()

    init {
        val file = URLDecoder.decode(javaClass.classLoader.getResource("keys.properties").file, "UTF-8")
        val input = FileInputStream(file)
        properties.load(input)
    }

    fun getProperty(key: String) = properties.getProperty(key)
}