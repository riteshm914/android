package com.demo.mvvm.app.data.repository

import com.demo.mvvm.app.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getFacts() =  apiHelper.getFacts()
}