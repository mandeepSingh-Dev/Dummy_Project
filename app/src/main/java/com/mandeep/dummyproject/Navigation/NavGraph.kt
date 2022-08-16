package com.mandeep.dummyproject.Navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mandeep.dummyproject.Screens.*

@Composable
fun setUpNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavScreen.SplashScreen.route){

        composable(route = NavScreen.SplashScreen.route)
        {
            SplashScreen(navHostController = navController){ route,value->
                navController.popBackStack(route = NavScreen.SplashScreen.route, inclusive = true)
                navController.navigate(route)
            }
        }
        composable(route = NavScreen.LoginScreen.route){
            LoginScreen(navHostController = navController)
        }
        composable(route = NavScreen.SingleWallpaperScreen.route)
        {
            SingleWallpaperScreen(navHostController = navHostControllerr)
        }
        composable(route = NavScreen.AllWallpaperScreen.route)
        {
            AllWallpaperScreen(navHostController = navController)
        }
    }
}