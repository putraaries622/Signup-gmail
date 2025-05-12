import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser(GlobalVariable.url)

WebUI.maximizeWindow()

WebUI.click(findTestObject('btn-create-account'))

WebUI.click(findTestObject('span-personal-use'))

WebUI.setText(findTestObject('txt-first-name'), fistname)

WebUI.setText(findTestObject('txt-last-name'), lasname)

WebUI.click(findTestObject('btn-next'))

WebUI.waitForElementPresent(findTestObject('lbl-basic-information'), 2)

WebUI.setText(findTestObject('txt-day'), day)

WebUI.setText(findTestObject('txt-year'), year)

WebUI.click(findTestObject('click-dropdown-month'))

WebUI.click(findTestObject('Object Repository/select-month', [('listMonth') : listMonth]))

WebUI.click(findTestObject('click-dropdown-gender'))

WebUI.click(findTestObject('Object Repository/select-gender', [('gender') : gender]))

WebUI.click(findTestObject('btn-next'))

WebUI.click(findTestObject('checkbox-your-own-gmail-address'))

// Misal username awal
String baseUsername = username

// Ambil angka terakhir di username
int counter = baseUsername.replaceAll('\\D+', '').toInteger()

String prefix = baseUsername.replaceAll('\\d', '')

// Loop sampai username valid
boolean isTaken = true

while (isTaken) {
    String username = counter.toString()

    WebUI.setText(findTestObject('txt-username'), prefix + username)

    WebUI.click(findTestObject('btn-next'))

    // Tunggu apakah muncul alert (maks 3 detik)
    isTaken = WebUI.verifyElementPresent(findTestObject('msg-That username is taken. Try another'), 3, FailureHandling.OPTIONAL)

    if (isTaken) {
        counter++ // tambahkan angka jika username masih dipakai
    }
}

WebUI.setText(findTestObject('txt-password'), password)

WebUI.setText(findTestObject('txt-confirm'), password)

WebUI.click(findTestObject('btn-next'))

WebUI.click(findTestObject('listbox-code-phone-number'))

WebUI.click(findTestObject('Object Repository/click-code-phone-number', [('codePhoneNumber') : codePhoneNumber]))

WebUI.setText(findTestObject('txt-phone-number'), phoneNumber)

WebUI.click(findTestObject('btn-next'))

