package com.example.cameratrapv2.database.MmsLoader

import android.content.Context
import com.example.cameratrapv2.models.UriImgData

class LoaderRepository(context: Context, numberList: List<String>) {

    private val loader = Loader(context, numberList)
    fun getAllMmsData(idList: List<String>): List<UriImgData> = loader.getAllMmsData(idList)

}