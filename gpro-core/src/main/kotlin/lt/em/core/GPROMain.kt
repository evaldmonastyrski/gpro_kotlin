package lt.em.core

import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {

    fun start() {
        LOGGER.info("Application has started")
        connect()
    }

    private fun connect() {
        val gproConnector = GPROConnector()
        try {
            gproConnector.login()
            gproConnector.getDriverData()
        } catch (exception: Exception) {
            LOGGER.error("WebDriver Exception. {}", exception.stackTrace)
        } finally {
            gproConnector.quit()
        }
    }
}

fun main() {
    GPROMain().start()
}

