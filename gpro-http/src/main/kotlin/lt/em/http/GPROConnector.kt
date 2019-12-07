package lt.em.http

import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROConnector::class.java)
private const val APPLE_WEB_DRIVER_PATH = "/usr/local/bin/chromedriver"
private const val WINODWS_WEB_DRIVER_PATH = "C:\\workspace\\\\chromedriver.exe"

class GPROConnector {
    val webDriverPath = if (isApple()) APPLE_WEB_DRIVER_PATH else WINODWS_WEB_DRIVER_PATH
}

private fun isApple(): Boolean {
    return getOsName().startsWith("Mac OS X")
}

private fun getOsName(): String {
    return System.getProperty("os.name")
}