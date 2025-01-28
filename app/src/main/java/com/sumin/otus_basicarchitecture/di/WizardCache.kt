package com.sumin.otus_basicarchitecture.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {
    private val userData = mutableMapOf<String, String>()

    fun saveData(key: String, value: String) {
        userData[key] = value
    }

    fun getData(key: String): String? {
        return userData[key]
    }
}