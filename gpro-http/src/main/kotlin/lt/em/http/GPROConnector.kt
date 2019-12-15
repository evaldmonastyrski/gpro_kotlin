package lt.em.http

import lt.em.datamodel.*
import lt.em.http.input.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROConnector::class.java)
private const val APPLE_WEB_DRIVER_PATH = "/usr/local/bin/chromedriver"
private const val WINDOWS_WEB_DRIVER_PATH = "C:\\workspace\\\\chromedriver.exe"
private const val WEB_DRIVER_KEY = "webdriver.chrome.driver"

class GPROConnector {
    init {
        val webDriverPath = if (isApple()) APPLE_WEB_DRIVER_PATH else WINDOWS_WEB_DRIVER_PATH
        System.setProperty(WEB_DRIVER_KEY, webDriverPath)
    }
    private val propertyValues = PropertyValues()
    init {
        LOGGER.info("My property values: Clean Login: {}, Flush Memory: {}, Headless: {}",
            propertyValues.isCleanLogin,
            propertyValues.isFlushMemory,
            propertyValues.isHeadless)
    }
    private val options = ChromeOptions()
    init {
        if (propertyValues.isHeadless) {
            options.addArguments("--headless",
                "--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors"
            )
        }
    }
    private val webDriver = ChromeDriver(options)
    private val loginConnector = LoginConnector(webDriver, propertyValues)
    private val driverConnector = DriverConnector(webDriver)
    private val carConnector = CarConnector(webDriver)
    private val practiseConditionsConnector = PractiseConditionsConnector(webDriver)
    private val facilitiesConnector = FacilitiesConnector(webDriver)
    private val practiseConnector = PractiseConnector(webDriver)
    private val practiseActivator = GPROActivator(webDriver)

    fun parseInputData(): InputData {
        val driver = getDriverData()
        val car = getCarData()
        val practiseConditions = getPractiseConditionsData()
        val staffAndFacilities = getStaffAndFacilitiesData()
        return InputData(driver, car, practiseConditions, staffAndFacilities)
    }

    fun drivePractiseLap(setup: Setup, tyreCompound: String) {
        practiseActivator.drivePractiseLap(setup, tyreCompound)
        LOGGER.info("Practise lap was driven successfully")
    }

    fun getPractiseData(): Practise {
        val practise: Practise = practiseConnector.parsePractise()
        LOGGER.info("Practise: {}", practise)
        return practise
    }

    fun updateCar(partsToUpdate: List<Pair<String, Int>>) {
        partsToUpdate.forEach { part -> LOGGER.info("Update ${part.first} to level ${part.second}") }
        carConnector.selectRequiredOptions(partsToUpdate)
    }

    fun quitWebDriver() {
        webDriver.quit()
    }

    fun login() {
        LOGGER.info("Connecting to GPRO website...")
        loginConnector.login()
    }

    private fun getDriverData(): Driver {
        val driver = driverConnector.parseDriverSkills()
        LOGGER.info("Driver skills: {}", driver)
        return driver
    }

    private fun getCarData(): Car {
        val car: Car = carConnector.parseCar()
        LOGGER.info("Car: {}", car)
        return car
    }

    private fun getPractiseConditionsData(): PractiseConditions {
        val practiseConditions: PractiseConditions = practiseConditionsConnector.parsePractiseConditions()
        LOGGER.info("Practise Conditions: {}", practiseConditions)
        return practiseConditions
    }

    private fun getStaffAndFacilitiesData(): StaffAndFacilities {
        val staffAndFacilities: StaffAndFacilities = facilitiesConnector.parseStaffAndFacilities()
        LOGGER.info("Staff & Facilities: {}", staffAndFacilities)
        return staffAndFacilities
    }
}

private fun isApple(): Boolean {
    return getOsName().startsWith("Mac OS X")
}

private fun getOsName(): String {
    return System.getProperty("os.name")
}