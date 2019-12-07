package lt.em

import org.slf4j.LoggerFactory

val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {

    fun start() {
        LOGGER.info("Application has started")


    }
}

fun main() {
    GPROMain().start()
}

