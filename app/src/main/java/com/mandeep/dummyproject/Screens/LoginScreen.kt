package com.mandeep.dummyproject.Screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.mandeep.dummyproject.Navigation.NavScreen
import com.mandeep.dummyproject.R

lateinit var  navHostControllerrr :NavHostController

@Composable
fun LoginScreen(navHostController: NavHostController)
{
    navHostControllerrr = navHostController
    val scrollableState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
         Scaffold(modifier = Modifier.fillMaxSize(),topBar = {
            /* TopAppBar(title = {}, modifier= Modifier
                 .padding(15.dp)
                 .clip(RoundedCornerShape(15.dp)),navigationIcon = { IconButton(onClick = { navHostController.popBackStack() },modifier = Modifier) {
                 Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24), contentDescription = "")
                }
             }, backgroundColor = colorResource(id = R.color.topBarColor))*/
         }, backgroundColor = Color(0xFBFEFD)) {
             Loginlayout(scrollableState)
         }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Loginlayout(scrollableState: ScrollState){

        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
            .background(colorResource(id = R.color.background)))
        {
            val (imageRef,welcomeRef,emailRef,passwordRef,loginRef,orRef,ssignupRef,textFieldColumnRef,buttonColumnRef,ref8,ref9,ref10) = createRefs()
            val welcomnText = remember{ mutableStateOf("Welcome Back...")}

            val painter = painterResource(id = R.drawable.windowblue)
            val imageRatio = painter.intrinsicSize.width / painter.intrinsicSize.height
            Image(painter = painter, contentDescription = "", modifier= Modifier
                .wrapContentSize()
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(emailRef.top)
                }
                .fillMaxWidth())

            Text(text = welcomnText.value, fontWeight = FontWeight.Bold, letterSpacing = 3.sp, textAlign = TextAlign.Center, color = Color.White,modifier = Modifier
                .padding(start = 30.dp, top = 80.dp)
                .constrainAs(welcomeRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)

                }, fontFamily = FontFamily.Cursive, fontSize = TextUnit(40f, TextUnitType.Sp))

            val email = remember { mutableStateOf("")}
            val password = remember { mutableStateOf("")}

            //textFieldColumnRef
            Column(modifier = Modifier
                .padding(top = 50.dp, start = 30.dp, end = 30.dp)
                .constrainAs(textFieldColumnRef) {
                    top.linkTo(imageRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttonColumnRef.top)
                })
            {
                OutlinedTextField(value = email.value,
                    onValueChange = { email.value = it },
                    label = {
                        Text(" Email ")
                    },
                    modifier = Modifier
                        /*.constrainAs(emailRef) {
                            top.linkTo(imageRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(passwordRef.top)
                        }*/
                       /* .padding(top = 50.dp, start = 30.dp, end = 30.dp)*/,
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.ic_baseline_email_24), contentDescription = "")},
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    maxLines = 3
                )

                val passwordTrailingIocn = remember { mutableStateOf(R.drawable.ic_baseline_remove_red_eye_24)}
                val passwordVisibility = remember { mutableStateOf(false)}

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text(" Password ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.ic_baseline_password_24), contentDescription = "")},
                    modifier = Modifier
                       /* .constrainAs(passwordRef) {
                            top.linkTo(emailRef.bottom)
                            start.linkTo(emailRef.start)
                            end.linkTo(emailRef.end)
                            // bottom.linkTo(loginRef.top)
                        }*/
                       /* .padding(top = 5.dp, bottom = 15.dp, start = 30.dp, end = 30.dp)*/,shape = RoundedCornerShape(20.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }, content = {
                            Icon(painter = painterResource(id = passwordTrailingIocn.value), contentDescription ="" )
                        })
                    },
                    visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
                )
            }

            Column(modifier = Modifier.constrainAs(buttonColumnRef){
                top.linkTo(textFieldColumnRef.bottom)
                start.linkTo(textFieldColumnRef.start)
                end.linkTo(textFieldColumnRef.end)
                bottom.linkTo(parent.bottom)
            }.padding(top = 40.dp)) {
                Button(onClick = {
                  //  navHostControllerrr.popBackStack()
                    navHostControllerrr.navigate(NavScreen.AllWallpaperScreen.route)
                },
                    modifier = Modifier
                        .fillMaxWidth()
                      /*  .constrainAs(loginRef) {
                            top.linkTo(textFieldColumnRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(orRef.top)
                        }*/
                        .padding(start = 30.dp, end = 30.dp),
                    shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(Color.Blue))
                {
                    Text(text = "LOGIN", color = Color.White)
                }

                Text(text = " or ",modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                  /*  .constrainAs(orRef) {
                        top.linkTo(loginRef.bottom)
                        start.linkTo(loginRef.start)
                        end.linkTo(loginRef.end)
                        bottom.linkTo(ssignupRef.top)
                    }*/
                    .padding(5.dp), textAlign = TextAlign.Center)

                Button(onClick = {
                   // navHostControllerrr.popBackStack()
                    navHostControllerrr.navigate(NavScreen.AllWallpaperScreen.route)
                },
                    modifier = Modifier
                        .fillMaxWidth()
                      /*  .constrainAs(ssignupRef) {
                            top.linkTo(orRef.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }*/
                        .padding( start = 30.dp, end = 30.dp),
                    shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(Color.White))
                {
                    Text(text = "SIGN UP", color = Color.Black)
                }
            }



    }

}

@Preview
@Composable
fun previewLatyout(){
    Loginlayout(scrollableState = rememberScrollState())
}