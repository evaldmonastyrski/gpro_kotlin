package lt.em.http

import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROConnector::class.java)
private const val APPLE_WEB_DRIVER_PATH = "/usr/local/bin/chromedriver"
private const val WINODWS_WEB_DRIVER_PATH = "C:\\workspace\\\\chromedriver.exe"
private const val WEB_DRIVER_KEY = "webdriver.chrome.driver"

class GPROConnector {
    init {
        val webDriverPath = if (isApple()) APPLE_WEB_DRIVER_PATH else WINODWS_WEB_DRIVER_PATH
        System.setProperty(WEB_DRIVER_KEY, webDriverPath)
    }
    val propertyValues = PropertyValues()
    init {
        LOGGER.info("My property values: Clean Login: {}, Flush Memory: {}, Headless: {}",
            propertyValues.isCleanLogin,
            propertyValues.isFlushMemory,
            propertyValues.isHeadless)
    }
}

private fun isApple(): Boolean {
    return getOsName().startsWith("Mac OS X")
}

private fun getOsName(): String {
    return System.getProperty("os.name")
}