package lt.em.http

import java.util.Properties

class PropertyValues {
    private val HTTP_PROPERTIES_PATH = "http.properties"
    private var cleanLogin = true
    private var flushMemory = false
    private var headless = true
    init {
        javaClass.classLoader.getResourceAsStream(HTTP_PROPERTIES_PATH).use {
            val properties = Properties()
            it.let { properties.load(it) }

            cleanLogin = properties.getProperty("clean_login")?.toBoolean() ?: cleanLogin
            flushMemory = properties.getProperty("flush_memory")?.toBoolean() ?: flushMemory
            headless = properties.getProperty("headless")?.toBoolean() ?: headless
        }
    }
    val isCleanLogin = cleanLogin
    val isFlushMemory = flushMemory
    val isHeadless = headless
}