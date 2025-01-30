package com.sumin.otus_basicarchitecture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.otus_basicarchitecture.dadata_key.API_KEY
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.network.DadataService
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.ADDRESS_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val dadataService: DadataService,
    private val wizardCache: WizardCache,
) : ViewModel() {

    private var isMyAddressChecked = false
    private var enableNextButton = false

    private val _addressSuggestions = MutableLiveData("")
    val addressSuggestions: LiveData<String> get() = _addressSuggestions

    fun fetchAddressSuggestions(query: String) {
        viewModelScope.launch {
            try {
                val response = dadataService.suggestAddress(query, "Token $API_KEY")
                val firstSuggestion = response.suggestions.firstOrNull()?.value ?: ""
                _addressSuggestions.value = firstSuggestion.ifEmpty {
                    "Не смогли найти адрес. Попробуйте позже."
                }
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error fetching suggestions: ${e.message}")
                _addressSuggestions.value = "Ошибка на сервере: ${e.message}"
            }
        }
    }

    fun onCheckBoxClicked(isChecked: Boolean) {
        isMyAddressChecked = isChecked
        enableNextButton = isChecked
    }

    fun isNextButtonEnabled(): Boolean {
        return enableNextButton
    }

    fun onNextButtonClicked() {
        saveData()
    }

    private fun saveData() {
        addressSuggestions.value?.let { wizardCache.saveData(ADDRESS_KEY, it) }
    }
}
