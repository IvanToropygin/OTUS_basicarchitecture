package com.sumin.otus_basicarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.ADDRESS_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.BIRTHDAY_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.INTEREST_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.NAME_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.SURNAME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    fun getSummary(): String {
        val firstName = wizardCache.getData(NAME_KEY)
        val lastName = wizardCache.getData(SURNAME_KEY)
        val birthDate = wizardCache.getData(BIRTHDAY_KEY)
        val address = wizardCache.getData(ADDRESS_KEY)
        val interests = wizardCache.getData(INTEREST_KEY)

        return """
            Имя: $firstName
            Фамилия: $lastName
            Дата рождения: $birthDate
            Адрес: $address
            Интересы: $interests
        """.trimIndent()
    }
}