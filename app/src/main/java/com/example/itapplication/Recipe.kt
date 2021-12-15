package com.example.itapplication

//Recipeというクラスを作成し、レシピ名(name)、所要時間(time_required)、画像(imageId)をパラメータを持つように実装
data class Recipe(
    val name: String,
    val time: Int,
    val favorite: Int
)