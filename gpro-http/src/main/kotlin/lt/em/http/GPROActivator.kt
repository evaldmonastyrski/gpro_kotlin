package lt.em.http

import lt.em.datamodel.Setup
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select

private const val PRACTISE_URL = "https://gpro.net/gb/Qualify.asp"
private const val QUALIFICATION2 = "https://gpro.net/gb/Qualify2.asp"

class GPROActivator(private val webDriver: WebDriver) {
    fun driveLap(setup: Setup,
                 tyreCompound: String,
                 lapType: LapType = LapType.PRACTISE,
                 qualificationRiskIndex: Int = 0,
                 fuel: Int = 0) {
        webDriver.get(if (lapType == LapType.Q2) QUALIFICATION2 else PRACTISE_URL)
        webDriver.findElement(By.name("FWing")).sendKeys(setup.frontWing.toString())
        webDriver.findElement(By.name("RWing")).sendKeys(setup.rearWing.toString())
        webDriver.findElement(By.name("Engine")).sendKeys(setup.engine.toString())
        webDriver.findElement(By.name("Brakes")).sendKeys(setup.brakes.toString())
        webDriver.findElement(By.name("Gear")).sendKeys(setup.gear.toString())
        webDriver.findElement(By.name("Suspension")).sendKeys(setup.suspension.toString())
        val dropdown = Select(
            webDriver.findElement(
                By.id("Tyres")
            )
        )
        dropdown.selectByVisibleText(tyreCompound)

        if (lapType == LapType.Q1) {
            additionalSetupForQ1(qualificationRiskIndex)
        }

        if (lapType == LapType.Q2) {
            additionalSetupForQ2(qualificationRiskIndex, fuel)
        }

        webDriver.findElement(By.name("DriveLap")).click()
    }

    private fun additionalSetupForQ1(index: Int) {
        webDriver.findElement(By.id("radioQual")).click()
        qualificationRisk(index)
    }

    private fun additionalSetupForQ2(index: Int, fuel: Int) {
        qualificationRisk(index)
        webDriver.findElement(By.id("Fuel")).sendKeys(fuel.toString())
    }

    private fun qualificationRisk(index: Int) {
        Select(webDriver.findElement(By.name("Risk"))).selectByIndex(index)
    }
}