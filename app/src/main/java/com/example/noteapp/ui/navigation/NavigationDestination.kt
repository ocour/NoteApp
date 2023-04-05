package com.example.noteapp.ui.navigation

/**
 *  Interface for Screens navigation info, each screen must implement one
 */
interface NavigationDestination {
    /**
     *  Unique route name
     */
    val route: String

    /**
     *  String resource id for screens TopAppBar title
     */
    val titleRes: Int
}