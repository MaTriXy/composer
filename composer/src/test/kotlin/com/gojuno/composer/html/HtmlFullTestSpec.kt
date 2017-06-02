package com.gojuno.composer.html

import com.gojuno.commander.android.AdbDevice
import com.gojuno.composer.AdbDeviceTest
import com.gojuno.composer.testFile
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import java.util.concurrent.TimeUnit.NANOSECONDS

class HtmlFullTestSpec : Spek({

    context("AdbDeviceTest.toHtmlTest") {

        val adbDeviceTest = AdbDeviceTest(
                adbDevice = AdbDevice(id = "testDevice", online = true),
                className = "com.gojuno.example.TestClass",
                testName = "test1",
                status = AdbDeviceTest.Status.Passed,
                durationNanos = 23000,
                logcat = testFile(),
                files = listOf(testFile(), testFile()),
                screenshots = listOf(testFile(), testFile())
        )

        val htmlTest = adbDeviceTest.toHtmlFullTest(htmlReportDir = testFile().parentFile)

        it("converts AdbDeviceTest to HtmlFullTest") {
            assertThat(htmlTest).isEqualTo(HtmlFullTest(
                    packageName = "com.gojuno.example",
                    className = "TestClass",
                    name = adbDeviceTest.testName,
                    status = HtmlFullTest.Status.Passed,
                    durationMillis = NANOSECONDS.toMillis(adbDeviceTest.durationNanos),
                    stacktrace = null,
                    logcatPath = adbDeviceTest.logcat.path,
                    filePaths = adbDeviceTest.files.map { it.path },
                    screenshotsPaths = adbDeviceTest.screenshots.map { it.path },
                    deviceId = adbDeviceTest.adbDevice.id,
                    properties = emptyMap()
            ))
        }
    }
})
