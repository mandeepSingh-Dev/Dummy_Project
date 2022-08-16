package com.mandeep.dummyproject.Screens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mandeep.dummyproject.R
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
lateinit var context:Context

lateinit var isSSaved:MutableState<Boolean>
lateinit var isProgress:MutableState<Boolean>

@Composable
fun SingleWallpaperScreen(navHostController: NavHostController, @DrawableRes downloadIcon:Int = R.drawable.ic_baseline_download_24, @DrawableRes doneIcon:Int = R.drawable.ic_baseline_download_done_24) {

    context = LocalContext.current
    val photoItem = navHostController.previousBackStackEntry?.arguments?.getParcelable<PhotoItem>("PHOTO_ITEM")

    val scrollState  = rememberScrollState()
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)
        .verticalScroll(scrollState)) {
        val (imageRef, textRef, iconRef, textfieldRef) = createRefs()

        Card(border = BorderStroke(
            0.5.dp,
            Brush.sweepGradient(
                listOf(
                    Color.White,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.DarkGray,
                    Color.Black,
                    Color.Cyan,
                    Color.Green,
                    Color.Magenta,
                    Color.LightGray
                )
            )
        ), shape = RoundedCornerShape(20.dp), elevation = 10.dp, modifier = Modifier
            .constrainAs(imageRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(20.dp)) {
            Box(contentAlignment = Alignment.Center)
            {
                AsyncImage(model = photoItem?.download_url,
                    contentDescription = " d",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)), onLoading = ({
                    })
                )
            }
        }

        Text(text = photoItem?.author.toString(), modifier = Modifier
            .constrainAs(textRef) {
                top.linkTo(imageRef.bottom)
                start.linkTo(imageRef.start)
                end.linkTo(imageRef.end)
                width = androidx.constraintlayout.compose.Dimension.fillToConstraints
            }, textAlign = TextAlign.Center
        )


        val icon = rememberSaveable() { mutableStateOf(downloadIcon) }
        val downloadStatusText = remember { mutableStateOf("DOWNLOAD") }
        isSSaved = remember{ mutableStateOf(false)}
         isProgress = remember{mutableStateOf(false)}

        downloadStatusText.value = if ( isSSaved.value ) "DOWNLOADED"  else  "DOWNLOAD"
        icon.value = if ( isSSaved.value ) R.drawable.ic_baseline_download_done_24 else R.drawable.ic_baseline_download_24

        var textt by remember{ mutableStateOf("")}
/*TextField(value = textt, onValueChange ={
    textt =
} )*/


        /*TextField(value = textt, onValueChange = {textt = it}, modifier = Modifier.constrainAs(textfieldRef){
            bottom.linkTo(iconRef.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })*/
        Button( onClick = {
            photoItem?.let { saveToMediaDevice(photoItem) }
                          isProgress.value = true
                          },
            enabled = !isSSaved.value,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .constrainAs(iconRef) {
                    start.linkTo(textRef.start)
                    end.linkTo(textRef.end)
                    bottom.linkTo(parent.bottom)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(Color.Magenta)) {
            Row(modifier =Modifier.align(Alignment.CenterVertically), horizontalArrangement = Arrangement.Center) {

               CircularProgressIndicator(color = Color.Black, strokeWidth = 1.dp,modifier = Modifier.alpha( if(isProgress.value) 1f else 0f ))
                Icon(
                    painter = painterResource(id = icon.value),
                    contentDescription = "iconStatus",
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = downloadStatusText.value, modifier = Modifier
//                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

    }

}


 fun saveToMediaDevice(photoItem: PhotoItem):Boolean {

    val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val contentResolver = context.contentResolver
    val contentValues = ContentValues()
    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,photoItem.author)
    contentValues.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg")
    contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,Environment.DIRECTORY_PICTURES)

    val insertedUri = contentResolver.insert(uri,contentValues)
    insertedUri?.let {
        val outt = contentResolver.openOutputStream(insertedUri)

        Glide.with(context).asBitmap().load(photoItem.download_url).into(object:CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                val bool = resource.compress(Bitmap.CompressFormat.JPEG,100,outt)
                isSSaved.value = bool
                isProgress.value = !bool

            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    return !isSSaved.value
}

     fun getBitmap(url:String):Bitmap{
         var bitmap:Bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)
         Glide.with(context).asBitmap().load(url).into(object:CustomTarget<Bitmap>(){
             override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap = resource
                 Log.d("fodmnfd",resource.toString())
             }
             override fun onLoadCleared(placeholder: Drawable?) {}
         })

         return bitmap
     }


@Preview
@Composable
fun preview(){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (imageRef, textRef, iconRef) = createRefs()

        Card(border = BorderStroke(0.5.dp, Brush.sweepGradient(listOf(Color.White,Color.Red,Color.Blue,Color.Yellow,Color.DarkGray,Color.Black,Color.Cyan,Color.Green,Color.Magenta,Color.LightGray))),shape = RoundedCornerShape(20.dp), elevation = 10.dp, modifier = Modifier
            .constrainAs(imageRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(20.dp)){
            Box(contentAlignment = Alignment.Center)
            {
                AsyncImage(model = R.drawable.guitar_wallpaper,
                    contentDescription =" d",
                    modifier = Modifier
                        .padding(20.dp)
                        .width(250.dp)
                        .height(250.dp)
                        .clip(RoundedCornerShape(20.dp)), onLoading = ({
                    }))
            }
        }

        Text(text = "dfodomnfd",modifier = Modifier
            .background(Color.Magenta)
            .constrainAs(textRef) {
                top.linkTo(imageRef.bottom)
                start.linkTo(imageRef.start)
                end.linkTo(iconRef.start)
                width = androidx.constraintlayout.compose.Dimension.fillToConstraints
            },textAlign = TextAlign.Center)


        val icon = rememberSaveable(){ mutableStateOf(R.drawable.ic_baseline_download_24) }
        IconButton(onClick = {
            icon.value = if(icon.value == R.drawable.ic_baseline_download_24) R.drawable.ic_baseline_download_done_24 else R.drawable.ic_baseline_download_done_24
        },modifier = Modifier
            .background(Color.Green)
//            .padding(end = 20.dp)
            .constrainAs(iconRef) {
                top.linkTo(textRef.top)
                end.linkTo(imageRef.end)
                //   start.linkTo(textRef.end)
            }) {
            Icon(painter = painterResource(id = icon.value), contentDescription = null)
        }

    }

}