package lt.em.core

import lt.em.datamodel.Car
import lt.em.datamodel.Driver
import lt.em.datamodel.PractiseConditions
import lt.em.datamodel.Setup
import lt.em.gpro.calculator.SetupCalculator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import lt.em.core.converters.convertSetup
import lt.em.core.converters.convertDriver
import lt.em.core.converters.convertPractiseConditions
import lt.em.core.converters.convertCar

private val LOGGER: Logger = LoggerFactory.getLogger(GPROMain::class.java)
private const val USE_EXTERNAL_VALUATION = true

fun calculateSetup(driver: Driver, car: Car, practiseConditions: PractiseConditions): Setup {
    if (USE_EXTERNAL_VALUATION) {
        val cSetup = SetupCalculator().calculate(
            convertDriver(driver),
            convertPractiseConditions(practiseConditions),
            convertCar(car)
        )

        val setup: Setup? = cSetup?.let { convertSetup(it) }
        LOGGER.info("Calculator package is available, setup for {}: {}",practiseConditions.trackName, setup)
        return setup ?: Setup()
    } else {
        val setup = Setup()
        LOGGER.info("Calculator package is unavailable, default setup will be used: {}", setup)
        return setup
    }
}



