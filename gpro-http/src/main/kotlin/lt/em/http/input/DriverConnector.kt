package lt.em.http.input

import lt.em.datamodel.Driver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select

private const val FROM_INDEX = 11
private const val TO_INDEX = 37
private const val DRIVER_URL = "https://gpro.net/gb/TrainingSession.asp"
private const val TRAIN_BUTTON = "TrainDriver"

class DriverConnector(private val webDriver: WebDriver) {

    companion object {
        private val trainingMap = hashMapOf(
            "FITN" to 0,
            "YOGA" to 1,
            "PR T" to 2,
            "TECH" to 3,
            "SPOR" to 4,
            "NINJ" to 5,
            "SPA " to 6
        )
    }

    fun parseDriverSkills(): Driver {
        enterDriverPage()
        val headers = webDriver.findElements(By.tagName("h1"))
        val driverName = headers[1].text.split(": ").toTypedArray()[1]
        val rows = webDriver.findElements(By.tagName("tr")).subList(FROM_INDEX, TO_INDEX)
        val driverSkills = mutableListOf<Int>()
        for (row in rows) {
            val rowEntry = row.text
            if (rowEntry.isNotEmpty()) {
                if (rowEntry.contains("%")) {
                    driverSkills.add(rowEntry.split("%")[0].toInt())
                    continue
                }
                driverSkills.add(rowEntry.split(":")[1].trim { it <= ' ' }.toInt())
            }
        }

        return Driver(name = driverName,
            energy = driverSkills[0],
            overall = driverSkills[1],
            concentration = driverSkills[2],
            talent = driverSkills[3],
            aggressiveness = driverSkills[4],
            experience = driverSkills[5],
            technicalInsight = driverSkills[6],
            stamina = driverSkills[7],
            charisma = driverSkills[8],
            motivation = driverSkills[9],
            reputation = driverSkills[10],
            weight = driverSkills[11],
            age = driverSkills[12])
    }

    fun trainDriver(trainingType: String) {
        enterDriverPage()
        trainingMap[trainingType]?.let { Select(webDriver.findElement(By.id("SessionType"))).selectByIndex(it) }
        val trainButton = webDriver.findElement(By.name(TRAIN_BUTTON))
        Thread.sleep(1000)
        trainButton.click()
    }

    private fun enterDriverPage() {
        webDriver.get(DRIVER_URL)
    }
}