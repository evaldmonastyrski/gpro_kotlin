package lt.em.core

import lt.em.http.GPROConnector
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.prefs.Preferences

private val LOGGER = LoggerFactory.getLogger(GPROMain::class.java)

class GPROMain {
    private val preferences = Preferences.userNodeForPackage(GPROMain:: class.java)

    fun start() {
        LOGGER.info("Application has started")
        val gproConnector = GPROConnector()
        try {
            gproConnector.login()

            while (true) {
                LOGGER.info("Choose Action: \n P - drive practise lap \n U - update car \n C - close application")

                when ((readLine() ?: "").toUpperCase()) {
                    "P" -> Actions.drivePractiseLap(gproConnector)
                    "U" -> Actions.updateCar(gproConnector, preferences)
                    else -> {
                        gproConnector.quitWebDriver()
                        LOGGER.info("Application is closing")
                        return
                    }
                }
            }
        } catch (exception: Exception) {
            LOGGER.error("Exception. {}", exception.stackTrace)
            gproConnector.quitWebDriver()
        }
    }
}

fun main() {
    GPROMain().start()
}

