package lt.em.core

import lt.em.datamodel.CombinedData
import lt.em.datamodel.InputData
import lt.em.gpro.persistence.GPROPersister
import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory
import java.util.prefs.Preferences

class Actions {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(Actions::class.java)
        private const val CAR_UPDATE_CONFIGURATION_KEY = "UpdateConfiguration"

        fun drivePractiseLap(gproConnector: GPROConnector) {
            val inputData = gproConnector.parseInputData()
            val setup = calculateSetup(inputData.driver, inputData.car, inputData.practiseConditions)
            LOGGER.info("Please enter the tyre compound you would like to use: \n")
            gproConnector.drivePractiseLap(setup, readLine() ?: "")
            persist(inputData, gproConnector)
        }

        fun updateCar(gproConnector: GPROConnector, preferences: Preferences) {
            val storedUpdateConfiguration = preferences.get(CAR_UPDATE_CONFIGURATION_KEY, "")
            val carUpdateConfiguration = (
                if (storedUpdateConfiguration.isNotEmpty()) checkSavedConf(storedUpdateConfiguration, preferences)
                else acceptCarUpdateConf(preferences)).split(",")

            val partsToUpdate: MutableList<Pair<String, Int>> = mutableListOf()
            carUpdateConfiguration.forEach { part ->
                val split = part.trim().split(" ")
                partsToUpdate.add(Pair(split[0].subSequence(0, 3).toString(), split[1].toInt()))
            }
            gproConnector.updateCar(partsToUpdate)
        }

        private fun checkSavedConf(storedUpdateConfiguration: String, preferences: Preferences): String {
            LOGGER.info("Would you like to use stored preferences for updating car? Y/N")
            val confirmation = (readLine() ?: "").toUpperCase()
            return if (confirmation == "Y") storedUpdateConfiguration else acceptCarUpdateConf(preferences)
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

        private fun acceptCarUpdateConf(preferences: Preferences): String {
            LOGGER.info("Enter components you would like to update in the following form: \n PT1 1,PT2 2,PT3 3")
            val carUpdateConfiguration = (readLine() ?: "").toUpperCase()
            preferences.put(CAR_UPDATE_CONFIGURATION_KEY, carUpdateConfiguration)
            return carUpdateConfiguration
        }
    }
}