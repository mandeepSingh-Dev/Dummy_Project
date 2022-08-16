package com.mandeep.dummyproject.Screens

import android.annotation.SuppressLint
import android.text.style.ScaleXSpan
import android.util.Log
import android.widget.GridLayout
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.mandeep.dummyproject.Navigation.NavScreen
import com.mandeep.dummyproject.R
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navHostController: NavHostController,navigate: (route:String,value:String)-> Unit) {

    splashlayout()
    LaunchedEffect(key1 = 1, block = {
          delay(3000)
          navHostController.popBackStack()
          navigate(NavScreen.LoginScreen.route,"From SplashScreen")
    })
}

@Composable
fun splashlayout(){


    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(Color.Magenta))
    {
        val (ref1,ref2,ref3,fullImage) = createRefs()

        Image(painter = painterResource(id = R.drawable.guitar_wallpaper),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .constrainAs(fullImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

        Image(painter = painterResource(id = R.drawable.wood_wallpapepr),
            contentDescription = "DummyProject",
            alignment = Alignment.Center,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .size(200.dp)
                .wrapContentSize()
                .constrainAs(ref1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .clip(RoundedCornerShape(25.dp))
        )
        LinearProgressIndicator(modifier = Modifier
            .padding(top = 10.dp)
            .constrainAs(ref2) {
                top.linkTo(ref1.bottom)
                start.linkTo(ref1.start)
                end.linkTo(ref1.end)
            },color = Color.White)
    }


}