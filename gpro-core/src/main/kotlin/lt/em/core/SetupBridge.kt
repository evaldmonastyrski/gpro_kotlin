package lt.em.core

import lt.em.datamodel.Car
import lt.em.datamodel.Driver
import lt.em.datamodel.PractiseConditions
import lt.em.datamodel.Setup
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(SetupBridge::class.java)

class SetupBridge {
    fun calculateSetup(driver: Driver, car: Car, practiseConditions: PractiseConditions): Setup {
        val setup = Setup()
        LOGGER.info("Calculator package is unavailable, default setup will be used: {}", setup)
        return setup
    }
}



