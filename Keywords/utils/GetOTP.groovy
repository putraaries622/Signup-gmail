package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class GetOTP {
	@Keyword
	def String fetchOTPFromSMS() {
		String otpCode = ""
		try {
			// Jalankan perintah ADB untuk mengambil SMS dari inbox
			Process process = Runtime.getRuntime().exec("adb shell content query --uri content://sms/inbox")
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))
			String line

			while ((line = reader.readLine()) != null) {
				// Cari baris yang mengandung OTP (biasanya angka 4-6 digit)
				def matcher = (line =~ /\b\d{4,6}\b/)
				if (matcher.find()) {
					otpCode = matcher.group()
					break
				}
			}
			reader.close()
		} catch (Exception e) {
			println("Gagal ambil OTP: " + e.getMessage())
		}

		println("OTP: " + otpCode)
		return otpCode
	}
}
