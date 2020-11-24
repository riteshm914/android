package com.demo.mvvm.app.data.api

import com.demo.mvvm.app.data.model.FactModel
import retrofit2.http.GET

interface ApiService {

    @GET("s/2iodh4vg0eortkl/facts.json")
    suspend fun getFacts(): FactModel
}