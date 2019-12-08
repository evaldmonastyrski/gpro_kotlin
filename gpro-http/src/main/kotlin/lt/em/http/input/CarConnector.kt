package lt.em.http.input

import lt.em.datamodel.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

private const val CAR_URL = "https://gpro.net/gb/UpdateCar.asp"

class CarConnector(private val webDriver: WebDriver) {
    fun parseCar(): Car {
        webDriver.get(CAR_URL)
        val carProperties = mutableListOf<Int>()
        val rows = webDriver.findElements(By.tagName("tr"))
        for (carCharacter in rows.get(2).getText().split(" ").toTypedArray()) {
            carProperties.add(carCharacter.toInt())
        }

        return Car(power = carProperties[0],
            handling = carProperties[1],
            acceleration = carProperties[2],
            chassis = Chassis(getLevel(rows[5], "newLvlCha"), getWear(rows[5], "newWearCha")),
            engine = Engine(getLevel(rows[6], "newLvlEng"), getWear(rows[6], "newWearEng")),
            frontWing = FrontWing(getLevel(rows[7], "newLvlFW"), getWear(rows[7], "newWearFW")),
            rearWing = RearWing(getLevel(rows[8], "newLvlRW"), getWear(rows[8], "newWearRW")),
            underbody = Underbody(getLevel(rows[9], "newLvlUB"), getWear(rows[9], "newWearUB")),
            sidepods = Sidepods(getLevel(rows[10], "newLvlSid"), getWear(rows[10], "newWearSid")),
            cooling = Cooling(getLevel(rows[11], "newLvlCoo"), getWear(rows[11], "newWearCoo")),
            gearbox = Gearbox(getLevel(rows[12], "newLvlGea"), getWear(rows[12], "newWearGea")),
            brakes = Brakes(getLevel(rows[13], "newLvlBra"), getWear(rows[13], "newWearBra")),
            suspension = Suspension(getLevel(rows[14], "newLvlSus"), getWear(rows[14], "newWearSus")),
            electronics = Electronics(getLevel(rows[15], "newLvlEle"), getWear(rows[15], "newWearEle"))
        )
    }
}

private fun getLevel(element: WebElement, id: String): Int {
    return element.findElement(By.id(id)).text.toInt()
}

private fun getWear(element: WebElement, id: String): Int {
    val text = element.findElement(By.id(id)).text
    return text.substring(0, text.length - 1).toInt()
}