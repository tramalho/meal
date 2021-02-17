package br.com.tramalho.data.provider

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Type

class LocalProviderTest {

    var moshi = Moshi.Builder().build()

    @MockK(relaxUnitFun = true)
    lateinit var mockSharedPref: SharedPreferences

    @MockK(relaxUnitFun = true)
    lateinit var mockSharedEditor: SharedPreferences.Editor

    lateinit var localProvider: LocalProvider

    var data : String = "[{}]"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        localProvider = LocalProvider(mockSharedPref, moshi)
    }

    @Test
    fun notShouldBeFetchCategories(){

        val mockMoshi = mockk<Moshi>()

        localProvider = LocalProvider(mockSharedPref, mockMoshi)

        every { mockSharedPref.getString(any(), any()) } returns ""

        val fetchCategories = localProvider.fetchCategories()

        assertTrue(fetchCategories.isEmpty())

        verify(exactly = 0) { mockMoshi.adapter(Type::class.java) }
    }

    @Test
    fun shouldBeFetchCategories(){

        every { mockSharedPref.getString(any(), any()) } returns data

        val fetchCategories = localProvider.fetchCategories()

        assertTrue(fetchCategories.isNotEmpty())
    }

    @Test
    fun notSaveCategories(){

        every { mockSharedPref.edit() } returns mockSharedEditor

        every { mockSharedEditor.putString(any(), any()) } returns mockSharedEditor

        localProvider.saveCategories(emptyList())

        verify(exactly = 1) { mockSharedEditor.putString(any(), any()) }
    }
}