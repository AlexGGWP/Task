package com.tryzens.task.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tryzens.task.base.BaseUtilityTests
import com.tryzens.task.di.MockTestAPIService
import com.tryzens.task.di.configureTestAppComponent
import com.tryzens.task.rest.NetworkResponseHandler
import com.tryzens.task.rest.api.APIs
import com.tryzens.task.rest.data.RepositoriesData
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ResponseTest : BaseUtilityTests() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val mAPIService: APIs by inject()
    val mockWebServer: MockWebServer by inject()

    lateinit var mockTestAPIService: MockTestAPIService
    lateinit var listOfRepositories: List<RepositoriesData>

    @Before
    fun start() {
        super.setUp()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun testReceivedDataResponse(): Unit = runBlocking {

        var successfulResponse = false
        var errorResponse = false

        mockNetworkResponseWithFileContent("mock_response.json", HttpURLConnection.HTTP_OK)

        mockTestAPIService = MockTestAPIService()

        when (val dataReceived = mockTestAPIService.getAllRepositories()) {
            is NetworkResponseHandler.Success -> {
                successfulResponse = true
                listOfRepositories = dataReceived.successData
            }
            is NetworkResponseHandler.Error -> {
                errorResponse = true
                dataReceived.exception.message
            }
        }

        //Check if response was success or not
        Assert.assertTrue(successfulResponse)
        Assert.assertFalse(errorResponse)

        //Check result
        Assert.assertNotNull(listOfRepositories)
        Assert.assertEquals(listOfRepositories.size, 30)
    }
}