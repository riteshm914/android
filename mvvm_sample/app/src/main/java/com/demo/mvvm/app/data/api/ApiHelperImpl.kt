package com.demo.mvvm.app.data.api

import com.demo.mvvm.app.data.model.FactModel
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getFacts(): FactModel = apiService.getFacts()

}