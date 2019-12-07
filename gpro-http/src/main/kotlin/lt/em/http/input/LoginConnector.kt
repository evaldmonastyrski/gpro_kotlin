package lt.em.http.input

import lt.em.http.PropertyValues
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import java.util.prefs.BackingStoreException
import java.util.prefs.Preferences

private val LOGGER = LoggerFactory.getLogger(LoginConnector::class.java)
private const val LOGIN_URL = "http://gpro.net/gb/Login.asp"
private const val USERNAME_FIELD = "textLogin"
private const val PASSWORD_FIELD = "textPassword"
private const val LOGIN_BUTTON = "LogonFake"
private const val USERNAME_KEY = "username"
private const val PASSWORD_KEY = "password"

class LoginConnector(private val webDriver: WebDriver, private val propertyValues: PropertyValues) {
    private val preferences = Preferences.userNodeForPackage(LoginConnector:: class.java)

    fun login() {
        webDriver.get(LOGIN_URL)
        val accountId = askForAccountId()
        fillTextfield(USERNAME_FIELD, USERNAME_KEY + accountId, "Please enter your username")
        fillTextfield(PASSWORD_FIELD, PASSWORD_KEY + accountId, "Please enter your password")
        clickLogin()
    }

    private fun askForAccountId(): String {
        LOGGER.info("Please enter account ID, that will be stored")
        val accountId = readLine()
        return accountId ?: ""
    }

    private fun fillTextfield(field: String, persistenceKey: String, message: String) {
        val webField = webDriver.findElement(By.name(field))
        val storedText = preferences.get(persistenceKey, null)
        clearPersistedValues()
        storedText?.let { if (!propertyValues.isCleanLogin) {
            webField.sendKeys(it)
            return
        } }
        LOGGER.info(message)
        val inputText = readLine()
        webField.sendKeys(inputText)
        preferences.put(persistenceKey, inputText)
    }

    private fun clickLogin() {
        val loginButton = webDriver.findElement(By.name(LOGIN_BUTTON))
        loginButton.click()
    }

    private fun clearPersistedValues() {
        if (propertyValues.isFlushMemory) {
            try {
                preferences.clear()
            } catch (backingStoreException: BackingStoreException) {
                LOGGER.error("Failed to clear preferences {}", backingStoreException)
            }
        }
    }
}