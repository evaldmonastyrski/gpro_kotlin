package lt.em

import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory

val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {

    fun start() {
        LOGGER.info("Application has started")
        GPROConnector()
    }
}

fun main() {
    GPROMain().start()
}

