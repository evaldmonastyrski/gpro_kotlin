package lt.em.core

import lt.em.core.converters.Converter
import lt.em.datamodel.Car
import lt.em.datamodel.Driver
import lt.em.datamodel.PractiseConditions
import lt.em.datamodel.Setup
import lt.em.gpro.calculator.SetupCalculator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(SetupBridge::class.java)
private const val USE_EXTERNAL_VALUATION = true

class SetupBridge {
    fun calculateSetup(driver: Driver, car: Car, practiseConditions: PractiseConditions): Setup {
        if (USE_EXTERNAL_VALUATION) {
            val converter = Converter()
            val cSetup = SetupCalculator().calculate(
                converter.convertDriver(driver),
                converter.convertPractiseConditions(practiseConditions),
                converter.convertCar(car)
            )
            var setup = Setup()
            if (cSetup != null) {
                setup = converter.convertSetup(cSetup)
            }
            LOGGER.info("Calculator package is available, setup for {}: {}",practiseConditions.trackName, setup)
            return setup.copy()
        } else {
            val setup = Setup()
            LOGGER.info("Calculator package is unavailable, default setup will be used: {}", setup)
            return setup
        }
    }
}



