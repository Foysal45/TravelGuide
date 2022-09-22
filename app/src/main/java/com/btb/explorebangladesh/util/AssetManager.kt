package com.btb.explorebangladesh.util

import android.content.Context
import com.google.gson.Gson
import com.btb.explorebangladesh.data.model.DistrictDto
import com.btb.explorebangladesh.gson.fromJson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    fun getDistricts(): List<DistrictDto> {
        val districtsText: String =
            context.assets.open("districts.json").bufferedReader().use { it.readText() }
        return if (districtsText.isNotEmpty()) {
            gson.fromJson(districtsText)
        } else {
            emptyList()
        }
    }
}