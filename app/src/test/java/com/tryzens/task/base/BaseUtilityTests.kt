package com.tryzens.task.base

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.InputStream
import java.io.Reader

abstract class BaseUtilityTests : KoinTest {

    private lateinit var mMockServerInstance: MockWebServer
    private var mShouldStart = false

    @Before
    open fun setUp() {
        startMockServer(true)
    }

    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int): Unit =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    private fun getJson(path: String): String {
        var content = ""
        val inputStream: InputStream = javaClass.getResourceAsStream("/$path")
        val reader = BufferedReader(inputStream.reader() as Reader)
        reader.use { reader ->
            content = reader.readText()
        }
        return content
    }

    private fun startMockServer(shouldStart: Boolean) {
        if (shouldStart) {
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    fun getMockWebServerUrl(): String = mMockServerInstance.url("/").toString()

    private fun stopMockServer() {
        if (mShouldStart) {
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown() {
        stopMockServer()
        stopKoin()
    }
}