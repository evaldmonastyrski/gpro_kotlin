package lt.em.http

import lt.em.datamodel.Car
import lt.em.datamodel.Driver
import lt.em.datamodel.PractiseConditions
import lt.em.datamodel.StaffAndFacilities
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

    fun login() {
        LOGGER.info("Connecting to GPRO website...")
        loginConnector.login()
    }

    fun getDriverData(): Driver {
        val driver = driverConnector.parseDriverSkills()
        LOGGER.info("Driver skills: {}", driver)
        return driver
    }

    fun getCarData(): Car {
        val car: Car = carConnector.parseCar()
        LOGGER.info("Car: {}", car)
        return car
    }

    fun getPractiseConditionsData(): PractiseConditions {
        val practiseConditions: PractiseConditions = practiseConditionsConnector.parsePractiseConditions()
        LOGGER.info("Practise Conditions: {}", practiseConditions)
        return practiseConditions
    }

    fun getStaffAndFacilitiesData(): StaffAndFacilities {
        val staffAndFacilities: StaffAndFacilities = facilitiesConnector.parseStaffAndFacilities()
        LOGGER.info("Staff & Facilities: {}", staffAndFacilities)
        return staffAndFacilities
    }

    fun quit() {
        webDriver.quit()
    }
}

private fun isApple(): Boolean {
    return getOsName().startsWith("Mac OS X")
}

private fun getOsName(): String {
    return System.getProperty("os.name")
}