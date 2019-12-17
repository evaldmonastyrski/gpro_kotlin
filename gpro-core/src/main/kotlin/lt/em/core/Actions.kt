package lt.em.core

import lt.em.datamodel.CombinedData
import lt.em.datamodel.InputData
import lt.em.gpro.persistence.GPROPersister
import lt.em.http.GPROConnector
import lt.em.http.LapType
import org.slf4j.LoggerFactory
import java.util.prefs.Preferences

class Actions {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(Actions::class.java)
        private const val CAR_UPDATE_CONFIGURATION_KEY = "UpdateConfiguration"

        fun driveLap(gproConnector: GPROConnector, lapType: LapType) {
            val inputData = gproConnector.parseInputData()
            val setup = calculateSetup(inputData.driver, inputData.car, inputData.practiseConditions)
            LOGGER.info("Please enter the tyre compound you would like to use: \n")
            val tyreCompound = readLine() ?: ""
            val qualificationRiskIndex = if (lapType != LapType.PRACTISE) {
                LOGGER.info("Please choose qualification 1 risk from 0 to 3: \n")
                readLine()?.toInt() ?: 0
            } else 0
            when (lapType) {
                LapType.PRACTISE -> {
                    gproConnector.drivePractiseLap(setup, tyreCompound)
                    persist(inputData, gproConnector)
                }
                LapType.Q1 -> gproConnector.driveQualification1Lap(setup, tyreCompound, qualificationRiskIndex)
                LapType.Q2 -> {
                    LOGGER.info("Please enter fuel for Qualification 2: \n")
                    gproConnector.driveQualification2Lap(
                        setup,
                        tyreCompound,
                        qualificationRiskIndex,
                        readLine()?.toInt() ?: 0
                    )
                }
                else -> LOGGER.info("Incorrect lap type")
            }
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

        fun trainDriver(gproConnector: GPROConnector) {
            gproConnector.trainDriver(acceptDriverTrainingType())
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

        private fun acceptDriverTrainingType(): String {
            LOGGER.info("Enter driver training type (minimum 4 first characters): ")
            return (readLine() ?: "").toUpperCase().slice(IntRange(0, 3))
        }
    }
}