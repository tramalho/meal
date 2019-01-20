package br.com.tramalho.meal.presentation.setup

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.tramalho.data.di.mockURLModule
import br.com.tramalho.data.provider.LocalProvider
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import java.util.*
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseInstrumentedTest : KoinTest {

    @MockK(relaxUnitFun = true)
    lateinit var localProvider: LocalProvider

    lateinit var mockWebServer: MockWebServer

/*
    @get:Rule
    val instantRule = InstantTaskExecutorRule()
*/

    @Before
    open fun setUp() {

        mockWebServer = MockWebServer()

        MockKAnnotations.init(this)

        mockWebServer.start(Constants.PORT)

        StandAloneContext.loadKoinModules(
            listOf(
                mockLocalProviderModule(),
                mockURLModule
            )
        )
    }

    @After
    fun tearDown() {
        if(::mockWebServer.isInitialized){
            mockWebServer.shutdown()
        }
    }

    fun setupMockWebServer(pathMock: String, delay: Long = 1, statusCode: Int = 200) {
        val json = openFile(pathMock)
        val mockResponse = MockResponse()

        mockResponse
            .setResponseCode(statusCode)
            .setBody(json)
            .throttleBody(Long.MAX_VALUE, delay, TimeUnit.MILLISECONDS)

        mockWebServer.enqueue(mockResponse)
    }

    private fun openFile(pathFile: String): String? {

        val classLoader = this.javaClass.classLoader
        val resourceAsStream = classLoader?.getResourceAsStream(pathFile)

        val result = StringBuilder("")

        val scanner = Scanner(resourceAsStream)

        while (scanner.hasNext()){
            result.append(scanner.next())
        }

        return result.toString()
    }

    private fun mockLocalProviderModule(): Module {

        return module(override = true) {
            factory { localProvider }
        }
    }
}