package br.com.tramalho.meal.presentation.setup

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.tramalho.data.provider.LocalProvider
import br.com.tramalho.data.di.mockWebServerNeworkModule
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


@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseInstrumentedTest : KoinTest {

    @MockK(relaxUnitFun = true)
    protected lateinit var localProvider: LocalProvider

    protected lateinit var mockWebServer: MockWebServer

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
        mockWebServer = MockWebServer()
        mockWebServer.start(Constants.PORT)

        StandAloneContext.loadKoinModules(
            listOf(
                mockWebServerNeworkModule,
                mockLocalProviderModule())
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    fun setupSuccessMock(pathMock: String) {

        val json = openFile(pathMock)

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))
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

        return module {
            factory(override = true) {
                localProvider
            }
        }
    }
}