package com.sumin.otus_basicarchitecture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.BIRTHDAY_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.NAME_KEY
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.SURNAME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    private val _isNextButtonEnabled = MutableLiveData(false)
    val isNextButtonEnabled: LiveData<Boolean> get() = _isNextButtonEnabled

    fun validateInput(firstName: String, lastName: String, birthDate: String): Boolean {
        val isValid = firstName.isNotEmpty() && lastName.isNotEmpty() && birthDate.isNotEmpty() && validateAge(birthDate)
        _isNextButtonEnabled.value = isValid
        return isValid
    }

    private fun validateAge(birthDate: String): Boolean {
        try {
            if (birthDate.trim().toInt() > 18) return true
        } catch (e: Exception) {
            Log.d("PersonalInfoViewModel","Проверка даты рождения упала с ошибкой: ${e.message}")
        }
        return false
    }

    fun saveData(name: String, surname: String, birthYear: String) {
        wizardCache.saveData(NAME_KEY, name)
        wizardCache.saveData(SURNAME_KEY, surname)
        wizardCache.saveData(BIRTHDAY_KEY, birthYear)
    }
}