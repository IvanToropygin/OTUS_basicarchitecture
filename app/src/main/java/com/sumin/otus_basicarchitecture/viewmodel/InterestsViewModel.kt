package com.sumin.otus_basicarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import com.sumin.otus_basicarchitecture.di.WizardCache
import com.sumin.otus_basicarchitecture.utils.Constants.Companion.INTEREST_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    fun saveData(interests: List<String>) {
        wizardCache.saveData(INTEREST_KEY, interests.joinToString(", "))
    }
}