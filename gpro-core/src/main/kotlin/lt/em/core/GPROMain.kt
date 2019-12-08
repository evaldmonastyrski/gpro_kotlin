package lt.em.core

import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {

    fun start() {
        LOGGER.info("Application has started")
        val inputData = GPROConnector().parseInputData()
        SetupBridge().calculateSetup(inputData.driver, inputData.car, inputData.practiseConditions)
    }
}

fun main() {
    GPROMain().start()
}

