package lt.em.core

import lt.em.datamodel.CombinedData
import lt.em.datamodel.InputData
import lt.em.gpro.persistence.GPROPersister
import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {

    fun start() {
        LOGGER.info("Application has started")
        val gproConnector = GPROConnector()

        while (true) {
            LOGGER.info("Choose Action: \n P - drive practise lap \n U - update car \n C - close application")
            val action = (readLine() ?: "").toUpperCase()

            if (action == "P") {
                val inputData = gproConnector.parseInputData()
                val setup = calculateSetup(inputData.driver, inputData.car, inputData.practiseConditions)
                LOGGER.info("Please enter the tyre compound you would like to use: \n")
                gproConnector.drivePractiseLap(setup, readLine() ?: "")
                persist(inputData, gproConnector)
            }

            if (action == "U") {
                LOGGER.info("This action is not supported yet")
            }

            if (action == "C") {
                gproConnector.quitWebDriver()
                LOGGER.info("Application is closing")
                return
            }
        }
    }
}

fun main() {
    GPROMain().start()
}

private fun persist(inputData: InputData, gproConnector: GPROConnector) {
    val combinedData = CombinedData(
        inputData.driver,
        inputData.car,
        inputData.practiseConditions,
        inputData.staffAndFacilities,
        gproConnector.getPractiseData()
    )
    val gproPersister = GPROPersister()
    gproPersister.persistCombinedData(combinedData)
    gproPersister.closeDatabaseConnection()
}

