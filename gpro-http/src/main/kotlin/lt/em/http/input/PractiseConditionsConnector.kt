package lt.em.http.input

import lt.em.datamodel.PractiseConditions
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

private const val RAIN_GIF_NAME = "wRain.gif"
private const val PRACTISE_URL = "https://gpro.net/gb/Qualify.asp"

class PractiseConditionsConnector(private val webDriver: WebDriver) {
    fun parsePractiseConditions(): PractiseConditions {
        webDriver.get(PRACTISE_URL)
        val trackId = webDriver.findElement(By.linkText("Track information"))
            .getAttribute("href").split("=").toTypedArray()[1].toInt()
        val weather = if (webDriver.findElement(By.name("WeatherQ"))
                .getAttribute("src").split("/images/").toTypedArray()[1] != RAIN_GIF_NAME
        ) "DRY" else "WET"
        val headers = webDriver.findElements(By.tagName("h2"))
        val trackName = headers[1].text.split(": ").toTypedArray()[1]
        val rows = webDriver.findElements(By.tagName("tr"))

        val trackPower = rows[0].findElements(By.tagName("img")).size
        val trackHandling = rows[1].findElements(By.tagName("img")).size
        val trackAcceleration = rows[2].findElements(By.tagName("img")).size

        val weatherInfo = rows[6].text.split(" ").toTypedArray()
        val temperature = weatherInfo[1].split("°").toTypedArray()[0].toInt()
        val humidity = weatherInfo[2].split("%").toTypedArray()[0].toInt()

        return PractiseConditions(trackName = trackName,
            trackId = trackId,
            trackPower = trackPower,
            trackHandling = trackHandling,
            trackAcceleration = trackAcceleration,
            temperatureQ1 = temperature,
            humidityQ1 = humidity,
            weather = weather)
    }
}