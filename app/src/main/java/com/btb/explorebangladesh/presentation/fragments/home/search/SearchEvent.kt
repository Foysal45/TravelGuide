package com.btb.explorebangladesh.presentation.fragments.home.search

sealed class SearchEvent {

    data class TitleChanged(val title: String) : SearchEvent()
    data class CategoryIdChanged(val categoryId: Int) : SearchEvent()

//    object Search: SearchEvent()

}