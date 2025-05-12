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
import java.io.BufferedReader
import java.io.InputStreamReader

import internal.GlobalVariable

public class GetOTP {
	@Keyword
	def static String fetchOTPFromSender(String senderName) {
		def process = ["adb", "shell", "content", "query", "--uri", "content://sms/inbox"].execute()
		def output = process.text
		def lines = output.readLines()

		String latestOTP = null
		long latestDate = 0L

		lines.each { line ->
			// Hanya ambil baris yang mengandung sender yang sesuai
			if (line.contains("address=${senderName}")) {
				def dateMatch = (line =~ /date=(\d+)/)
				def bodyMatch = (line =~ /body=(.+?)(?= \w+=|$)/)

				if (dateMatch && bodyMatch) {
					long smsDate = dateMatch[0][1].toLong()
					String bodyText = bodyMatch[0][1]

					// Ambil OTP (dengan atau tanpa G- prefix)
					def otpMatch = (bodyText =~ /\b(?:G-)?(\d{4,8})\b/)

					if (otpMatch) {
						if (smsDate > latestDate) {
							latestDate = smsDate
							latestOTP = otpMatch[0][0]
						}
					}
				}
			}
		}

		if (latestOTP != null) {
			println "OTP dari ${senderName}: ${latestOTP}"
			return latestOTP
		} else {
			println "Tidak ditemukan OTP dari ${senderName}"
			return ""
		}
	}
}
