package com.sumin.otus_basicarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.viewmodel.PersonalInfoViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RunWith(MockitoJUnitRunner::class)
class PersonalInfoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var wizardCache: WizardCache

    private lateinit var viewModel: PersonalInfoViewModel

    @Before
    fun setup() {
        wizardCache = WizardCache()
        viewModel = PersonalInfoViewModel(WizardCache())
    }

    @Test
    fun `validateInput should enable button when all fields valid`() {
        // Valid data: age 20 years
        val testDate = LocalDate.now().minusYears(20)
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        val isValid = viewModel.validateInput("John", "Doe", testDate)

        assertTrue(isValid)
        assertTrue(viewModel.isNextButtonEnabled.value == true)
    }

    @Test
    fun `validateInput should disable button when empty fields`() {
        val isValid = viewModel.validateInput("", "", "")

        assertFalse(isValid)
        assertTrue(viewModel.isNextButtonEnabled.value == false)
    }

    @Test
    fun `validateAge should return false when under 18`() {
        val birthDate =
            LocalDate.now().minusYears(17).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        assertFalse(viewModel.validateInput("John", "Doe", birthDate))
    }

    @Test
    fun `validateAge edge case exactly 18 years`() {
        val birthDate = LocalDate.now().minusYears(18)
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        assertTrue(viewModel.validateInput("John", "Doe", birthDate))
    }
}