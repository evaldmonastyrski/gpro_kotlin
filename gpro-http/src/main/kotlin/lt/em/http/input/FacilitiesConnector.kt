package lt.em.http.input

import lt.em.datamodel.Facilities
import lt.em.datamodel.Staff
import lt.em.datamodel.StaffAndFacilities
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

private const val FACILITIES_URL = "https://gpro.net/gb/StaffAndFacilities.asp"
private const val LAST_STAFF_FACILITIES_INDEX = 28

class FacilitiesConnector(private val webDriver: WebDriver) {
    fun parseStaffAndFacilities(): StaffAndFacilities {
        webDriver.get(FACILITIES_URL)

        val properties = webDriver.findElements(By.tagName("tr"))
            .subList(0, LAST_STAFF_FACILITIES_INDEX)
            .filter { webElement: WebElement -> webElement.text.isNotEmpty() }
            .map { value: WebElement -> value.text.split(": ")
                .toTypedArray()[1]
                .dropWhile { char: Char -> char == ' ' }
                .toInt() }

        return StaffAndFacilities(overall = properties[0],
            staff = Staff(experience = properties[1],
                motivation = properties[2],
                technicalSkill = properties[3],
                stressHandling = properties[4],
                concentration = properties[5],
                efficiency = properties[6]),
            facilities = Facilities(windTunner = properties[7],
                pitstopTrainingCentre = properties[8],
                rnDWorkshop = properties[9],
                rnDDesignCentre = properties[10],
                engineeringWorkshop = properties[11],
                alloyAndChemicalLab = properties[12],
                commercial = properties[13])
        )
    }
}