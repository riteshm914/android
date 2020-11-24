package com.demo.mvvm.app.data.api

import com.demo.mvvm.app.data.model.FactModel

interface ApiHelper {

    suspend fun getFacts(): List<FactModel>
}