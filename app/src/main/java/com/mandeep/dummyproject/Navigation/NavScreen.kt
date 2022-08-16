package com.mandeep.dummyproject.Navigation

sealed class NavScreen(val route:String) {
    object SplashScreen:NavScreen("SplashScreen")
    object LoginScreen:NavScreen("LoginScreen")
    object AllWallpaperScreen:NavScreen("AllWallpaperScreen")
    object SingleWallpaperScreen :NavScreen("SingleWallpaperScreen")

}