package com.example.myapplication.Model

import java.io.Serializable

data class ItemsModel(
    var title:String="",
    var description:String="",
    var categoryId:String="",
    var picUrl:ArrayList<String> = ArrayList(),
    var price:Double=0.0
):Serializable