package lt.em.http.input

import lt.em.datamodel.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

private const val CAR_URL = "https://gpro.net/gb/UpdateCar.asp"
private const val UPDATE_BUTTON = "btnUpdateCar"

class CarConnector(private val webDriver: WebDriver) {

    private val partsMap = hashMapOf(
        "CHA" to Pair(5, "BuyChassis"),
        "ENG" to Pair(6, "BuyEngine"),
        "FRO" to Pair(7, "BuyFWing"),
        "REA" to Pair(8, "BuyRWing"),
        "UND" to Pair(9, "BuyUnderbody"),
        "SID" to Pair(10, "BuySidepods"),
        "COO" to Pair(11, "BuyCooling"),
        "GEA" to Pair(12, "BuyGear"),
        "BRA" to Pair(13, "BuyBrakes"),
        "SUS" to Pair(14, "BuySusp"),
        "ELE" to Pair(15, "BuyElectronics"))

    fun parseCar(): Car {
        val rows = listCarPartElements()
        val carProperties = mutableListOf<Int>()
        for (carCharacter in rows[2].text.split(" ").toTypedArray()) {
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

    fun selectRequiredOptions(partsToUpdate: List<Pair<String, Int>>) {
        val listCarPartElements = listCarPartElements()
        partsToUpdate.forEach {  part -> partsMap[part.first]?.let {
            Select(listCarPartElements[it.first].findElement(By.id(it.second))).selectByIndex(part.second) } }
        val updateButton = webDriver.findElement(By.name(UPDATE_BUTTON))
        Thread.sleep(1000)
        updateButton.click()
        val activeElement = webDriver.switchTo().activeElement()
        val confirmButton = activeElement.findElements(By.tagName("button"))[0]
        Thread.sleep(1000)
        confirmButton.click()
    }

    private fun listCarPartElements(): List<WebElement> {
        webDriver.get(CAR_URL)
        return webDriver.findElements(By.tagName("tr"))
    }
}

private fun getLevel(element: WebElement, id: String): Int {
    return element.findElement(By.id(id)).text.toInt()
}

private fun getWear(element: WebElement, id: String): Int {
    val text = element.findElement(By.id(id)).text
    return text.substring(0, text.length - 1).toInt()
}