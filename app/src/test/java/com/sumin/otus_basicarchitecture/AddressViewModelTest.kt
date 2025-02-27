package com.sumin.otus_basicarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.network.DadataService
import com.sumin.otus_basicarchitecture.network.models.AddressSuggestion
import com.sumin.otus_basicarchitecture.network.models.SuggestionsResponse
import com.sumin.otus_basicarchitecture.viewmodel.AddressViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AddressViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AddressViewModel(mockDadataService, mockWizardCache)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Mock
    private lateinit var mockDadataService: DadataService

    @Mock
    private lateinit var mockWizardCache: WizardCache

    private lateinit var viewModel: AddressViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `fetchAddressSuggestions should update LiveData with first suggestion on success`() =
        runBlocking {
        val mockResponse = SuggestionsResponse(
            suggestions = listOf(
                AddressSuggestion(value = "г Москва, ул Ленина"),
                AddressSuggestion(value = "г Санкт-Петербург, Невский пр-т")
            )
        )
        whenever(mockDadataService.suggestAddress(any())).thenReturn(mockResponse)

        viewModel.fetchAddressSuggestions("Москва")

        assertEquals("г Москва, ул Ленина", viewModel.addressSuggestions.value)
    }

    @Test
    fun `fetchAddressSuggestions should show error message when response is empty`() = runBlocking {
        whenever(mockDadataService.suggestAddress(any())).thenReturn(SuggestionsResponse(emptyList()))

        viewModel.fetchAddressSuggestions("InvalidQuery")

        assertEquals("Не смогли найти адрес. Попробуйте изменить ввод.", viewModel.addressSuggestions.value)
    }
}