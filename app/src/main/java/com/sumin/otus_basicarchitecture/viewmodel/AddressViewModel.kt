package com.sumin.otus_basicarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.ADDRESS_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.CITY_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.COUNTRY_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    fun validateInput(country: String, city: String, address: String): Boolean {
        return country.isNotEmpty() && city.isNotEmpty() && address.isNotEmpty()
    }

    fun saveData(country: String, city: String, address: String) {
        wizardCache.saveData(COUNTRY_KEY, country)
        wizardCache.saveData(CITY_KEY, city)
        wizardCache.saveData(ADDRESS_KEY, address)
    }
}