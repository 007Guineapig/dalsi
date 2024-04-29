package com.ahmedapps.roomdatabase.theme
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ahmedapps.roomdatabase.R

import java.net.URLDecoder




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Recept(navController: NavHostController,
           backStackEntry: NavBackStackEntry){

    val Nazov = backStackEntry.arguments?.getString("Nazov")
    val jpg = backStackEntry.arguments?.getString("jpg")
    val postup_ = backStackEntry.arguments?.getString("postup")
    val ingrediencie1 = backStackEntry.arguments?.getString("ingrediencie")
    val ingrediencie = URLDecoder.decode(ingrediencie1, "UTF-8")
    val postup = URLDecoder.decode(postup_,"UTF-8")
    val decodedUrl = URLDecoder.decode(jpg, "UTF-8")
    val strind = backStackEntry.arguments?.getString("strind")

    Column(

    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            //contentAlignment = Alignment.BottomStart
        ) {


            Card {
                Box{
                Image(
                    painter = rememberImagePainter(
                        data = decodedUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                    /*
                    ClickableHeartIcon(
                        modifier = Modifier
                            // .align(alignment = Alignment.TopStart)
                            .padding(16.dp)
                            .size(48.dp),
                        onHeartClicked = { /* Handle heart click here */ }
                    )

                     */
                    Icon(
                        imageVector = if (strind == "cau") Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp) // Adjust size as needed
                            .padding(4.dp), // Add padding if necessary
                                tint = if (strind == "cau") Color.White else Color.Black


                    )
                }



                //Spacer(modifier = Modifier.height(16.dp))
                if (ingrediencie != null && Nazov != null) {
                    aha(ingrediencie,Nazov)

                }
            }
        }


        val resultArray = remember { postup.split(Regex("\\d+\\.")) }
        var currentPage by remember { mutableStateOf(0) }

        val pagerState = rememberPagerState(pageCount = { resultArray.size })

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()

            ) { page ->
                currentPage = page

                Box(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .background(if (page == currentPage) Color.White else Color.Transparent)
                ) {
                    Column{

                    Text(
                        text = resultArray[page],
                        textAlign = TextAlign.Center
                    )
                    }
                }
            }

        }

    }
    }

    @Composable
    private fun aha(popis_:String,nazov:String) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier

                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {

                    Text(
                        text = nazov,
                        lineHeight = 30.sp,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(15.dp),

                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start

                    )
//asdas
                if (expanded) {

                    Text(
                        text = "Ingrediencie",
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start

                    )
                    LazyColumn {
                        item{
                        Text(
                            text = popis_
                        )
                    }}

                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }
