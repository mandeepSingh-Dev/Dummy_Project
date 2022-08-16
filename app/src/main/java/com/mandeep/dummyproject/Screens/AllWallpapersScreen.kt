package com.mandeep.dummyproject.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.mandeep.dummyproject.Navigation.LifecycleEvent
import com.mandeep.dummyproject.Navigation.NavScreen
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItem
import com.mandeep.dummyproject.Retrofit.RetrofitViewModel
import com.mandeep.dummyproject.ui.theme.gunMetal

import javax.inject.Inject


val listtt = arrayListOf("dfdfd1","dfdfd2","dfdf3","dfdfd4","dfdfd5","dfdfd6","dfdfd7","dfdf8")
var count = 0

var photoList = mutableStateListOf<PhotoItem>()
lateinit var lazyPhotoItems:LazyPagingItems<PhotoItem>
lateinit var navHostControllerr: NavHostController

@SuppressLint("UnrememberedMutableState")
@Composable
fun AllWallpaperScreen(navHostController: NavHostController, viewmodel:RetrofitViewModel = hiltViewModel() )
{
    navHostControllerr = navHostController

    //Log.d("dinfdg",viewmodel.liveList.observeAsState().value?.size.toString())

     lazyPhotoItems = viewmodel.pager.collectAsLazyPagingItems()
    Log.d("dfidgv", lazyPhotoItems.itemCount.toString())

    viewmodel.liveList.observeAsState().value?.let {
        photoList.addAll(it)
    }

    val listStateList =  mutableStateListOf("string")
        listStateList.clear()
        listStateList.addAll(listtt)

    LifecycleEvent(onEvent = {  owner: LifecycleOwner, event: Lifecycle.Event ->
        when(event)
        {
            Lifecycle.Event.ON_START -> {    Log.d("dkvnfdvfd","ON_START")    }
            Lifecycle.Event.ON_RESUME -> {
                Log.d("dkvnfdvfd","ON_RESUME")
               // listtt.add("On_Pause_String $count" )
                listStateList.add("On_Pause_String $count" )
                count++
                Log.d("dfidnfd",count.toString())}
               Lifecycle.Event.ON_PAUSE -> { Log.d("fgknfg","ON_PAUSE") }
               Lifecycle.Event.ON_STOP -> { Log.d("fgknfg","ON_STOP") }
            Lifecycle.Event.ON_CREATE -> { Log.d("fgknfg","ON_CREATE") }
            Lifecycle.Event.ON_ANY -> { Log.d("fgknfg","ON_ANY") }
            Lifecycle.Event.ON_DESTROY -> { Log.d("dkvnfdvfd","ON_DESTROY") }

        }
    })
    layout(listStateList)

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun layout( /*listRem : MutableState<ArrayList<String>>*/ listRem: SnapshotStateList<String>)
{


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topCardView,lazyVerticalColumn) = createRefs()
        

        Card( shape = RoundedCornerShape(20.dp),elevation = 5.dp,modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .constrainAs(topCardView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        {
            Text("Wallpapers",
                color = if(isSystemInDarkTheme()) Color.White else gunMetal,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(10.dp),
                fontSize = 30.sp,
                letterSpacing = 10.sp,
                maxLines = 1,
               textAlign = TextAlign.Center
            )
        }

       /* LazyVerticalGrid(cells = GridCells.Fixed(2),modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .constrainAs(lazyVerticalColumn) {
                top.linkTo(topCardView.bottom)
                start.linkTo(topCardView.start)
                end.linkTo(topCardView.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
            .padding(bottom = 10.dp))
        {

           *//* items(items = listRem){ string->
                cardItem(text = string)
            }*//*
            items(items = photoList){ photoItem->
                cardItem( photoItem)
            }
        }*/
        LazyColumn(modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .constrainAs(lazyVerticalColumn) {
                top.linkTo(topCardView.bottom)
                start.linkTo(topCardView.start)
                end.linkTo(topCardView.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
            .padding(bottom = 10.dp))
        {
            items(items = lazyPhotoItems){ photoItem ->
                Log.d("foodmnmfd",photoItem?.author.toString())
                photoItem?.let {
                    cardItem(photoItem = it)
                }

            }
        }



    }
}


@Composable
fun cardItem(photoItem: PhotoItem){
  val context = LocalContext.current
    Log.d("dignd",photoItem.author)
    Box(modifier = Modifier.fillMaxWidth())
    {

        Card( shape = RoundedCornerShape(23.dp), elevation = 12.dp, modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                navHostControllerr.currentBackStackEntry?.arguments?.putParcelable("PHOTO_ITEM",photoItem)
                navHostControllerr.navigate(NavScreen.SingleWallpaperScreen.route)
            }

        )
        {
          //  ConstraintLayout() {
              //  val (imageRef, authorRef) = createRefs()

            Column() {

         /*       GlideImage(imageModel = photoItem.download_url, shimmerParams =  ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = Color.White,
                    durationMillis = 200,
                    dropOff = 0.65f,
                    tilt = 20f),
                    circularReveal = CircularReveal(),
                    failure = {Text("Error")}
                )*/

               // GlideImage(imageModel = photoItem.download_url, circularReveal = CircularReveal(), bitmapPalette = BitmapPalette(photoItem.download_url,true))

                 AsyncImage(model = photoItem.download_url,
                     contentDescription =" d",
                     modifier = Modifier.padding(13.dp)
                         .wrapContentSize()
                         .wrapContentHeight()
                         .wrapContentWidth().clip(RoundedCornerShape(20.dp)), onLoading = ({
                     }))
               // Image(painter = rememberAsyncImagePainter(model = "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png"), contentDescription = "Hello",modifier = Modifier)
               Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxWidth().padding(20.dp)){
                   Text(
                       photoItem.author,
                       color =  if(isSystemInDarkTheme()) Color.White else gunMetal ,
                       fontWeight = FontWeight.ExtraBold,
                       fontFamily = FontFamily.Cursive,
                       modifier = Modifier/*.padding(10.dp).constrainAs(authorRef){
                        top.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }*/,
                       // fontSize = 30.sp,
                       letterSpacing = if(photoItem.author.contains("On_Pause_String")) 0.sp else 2.sp,
                       maxLines = 1,
                       textAlign = TextAlign.Center,
                   )
               }

            }

          //  }

        }
    }
    
    
}


@Preview
@Composable
fun previewLayout(){
  //  layout()
}