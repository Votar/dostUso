package com.entrego.entregouser.ui.favorites


interface FavoritesController {
    fun selectedAddress(address: String)
    fun showFavoritesFragment()
    fun hideFavoritesFragment()

}