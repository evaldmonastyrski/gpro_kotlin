package lt.em.core

import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {

    fun start() {
        LOGGER.info("Application has started")
        val gproConnector = GPROConnector()
        val inputData = gproConnector.parseInputData()
        val setup = SetupBridge().calculateSetup(inputData.driver, inputData.car, inputData.practiseConditions)
        LOGGER.info("Please enter the tyre compound you would like to use: \n")
        gproConnector.drivePractiseLap(setup, readLine() ?: "")
    }
}

fun main() {
    GPROMain().start()
}

