package lt.em.http.input

import lt.em.datamodel.Practise
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

private const val PRACTISE_URL = "https://gpro.net/gb/Qualify.asp"
private const val LAP_INDEX = 14
private const val SECONDS_IN_MINUTE = 60

class PractiseConnector(private val webDriver: WebDriver) {

    fun parsePractise(): Practise {
        webDriver.get(PRACTISE_URL)
        val rows = webDriver.findElements(By.tagName("tr"))
        val lapsDriven = rows[11].text.subSequence(30, 31).toString().toInt()
        if (lapsDriven != 0) {
            val practiseLapDetails =
                rows[LAP_INDEX + lapsDriven - 1].text.split(" ").toTypedArray()

            return Practise(attempt = lapsDriven,
                netTime = parseLaptime(practiseLapDetails[3]),
                frontWingSetup = practiseLapDetails[4].toInt(),
                rearWingSetup = practiseLapDetails[5].toInt(),
                engineSetup = practiseLapDetails[6].toInt(),
                brakesSetup = practiseLapDetails[7].toInt(),
                gearSetup = practiseLapDetails[8].toInt(),
                suspensionSetup = practiseLapDetails[9].toInt(),
                tyreType = practiseLapDetails[10])
        }
        return Practise(attempt = 0,
            netTime = 0.0,
            frontWingSetup = 0,
            rearWingSetup = 0,
            engineSetup = 0,
            brakesSetup = 0,
            gearSetup = 0,
            suspensionSetup = 0,
            tyreType = "")
    }

    private fun parseLaptime(laptimeString: String): Double {
        val length = laptimeString.length
        val split = laptimeString.substring(0, length - 1).split(":").toTypedArray()
        return split[0].toDouble() * SECONDS_IN_MINUTE + split[1].toDouble()
    }
}