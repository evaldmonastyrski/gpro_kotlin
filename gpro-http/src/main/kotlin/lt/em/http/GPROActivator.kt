package lt.em.http

import lt.em.datamodel.Setup
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select

private const val PRACTISE_URL = "https://gpro.net/gb/Qualify.asp"

class GPROActivator(private val webDriver: WebDriver) {
    fun driveQ1Lap(setup: Setup,
                   tyreCompound: String,
                   lapType: LapType = LapType.PRACTISE,
                   qualificationRiskIndex: Int = 0) {
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
        if (lapType == LapType.Q1) {
            additionalSetupForQ1(qualificationRiskIndex)
        }
        webDriver.findElement(By.name("DriveLap")).click()
    }

    private fun additionalSetupForQ1(index: Int) {
        webDriver.findElement(By.id("radioQual")).click()
        Select(webDriver.findElement(By.name("Risk"))).selectByIndex(index)
    }
}