package com.entrego.entregouser.ui.profile.favorites


interface FavoritesController {
    fun selectedAddress(address: String)
    fun showFavoritesFragment()
    fun hideFavoritesFragment()

}