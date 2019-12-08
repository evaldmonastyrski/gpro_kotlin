package lt.em.http

import lt.em.datamodel.Setup
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select

private const val PRACTISE_URL = "https://gpro.net/gb/Qualify.asp"

class GPROActivator(private val webDriver: WebDriver) {
    fun drivePractiseLap(setup: Setup, tyreCompound: String) {
        webDriver.get(PRACTISE_URL)
        webDriver.findElement(By.name("FWing")).sendKeys(java.lang.String.valueOf(setup.frontWing))
        webDriver.findElement(By.name("RWing")).sendKeys(java.lang.String.valueOf(setup.rearWing))
        webDriver.findElement(By.name("Engine")).sendKeys(java.lang.String.valueOf(setup.engine))
        webDriver.findElement(By.name("Brakes")).sendKeys(java.lang.String.valueOf(setup.brakes))
        webDriver.findElement(By.name("Gear")).sendKeys(java.lang.String.valueOf(setup.gear))
        webDriver.findElement(By.name("Suspension")).sendKeys(java.lang.String.valueOf(setup.suspension))
        val dropdown = Select(
            webDriver.findElement(
                By.id("Tyres")
            )
        )
        dropdown.selectByVisibleText(tyreCompound)
        webDriver.findElement(By.name("DriveLap")).click()
    }
}