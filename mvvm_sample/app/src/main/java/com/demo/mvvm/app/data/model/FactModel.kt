package com.demo.mvvm.app.data.model

import com.squareup.moshi.Json


data class FactModel(
    @Json(name  = "title")
    val title: String = "",
    @Json(name = "rows")
    val rows: Rows
)

data class Rows(
    @Json(name  = "title")
    val title: String = "",
    @Json(name  = "description")
    val description: String = "",
    @Json(name  = "imageHref")
    val imageHref: String = ""
)