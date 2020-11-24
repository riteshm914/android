package com.demo.mvvm.app.utils

import com.demo.mvvm.app.data.model.FactModel
import com.demo.mvvm.app.data.model.Rows


object TestUtil {
    fun createFact(title: String): FactModel {

        var rowList = ArrayList<Rows>()

        val row = Rows(
            "title",
            "description",
            ""
        )
        rowList.add(row)
        return FactModel(
            title, rowList
        )
    }

    val facts: FactModel
        get() {
            return createFact("Test Title")
        }
}