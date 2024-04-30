package com.ahmedapps.roomdatabase.theme
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

    val nazov = backStackEntry.arguments?.getString("Nazov")
    val jpg = backStackEntry.arguments?.getString("jpg")
    val postup_ = backStackEntry.arguments?.getString("postup")
    val ingrediencie_ = backStackEntry.arguments?.getString("ingrediencie")
    val ingrediencie = URLDecoder.decode(ingrediencie_, "UTF-8")
    val postup = URLDecoder.decode(postup_,"UTF-8")
    val decodedUrl = URLDecoder.decode(jpg, "UTF-8")
    val liked = backStackEntry.arguments?.getString("strind")

    Column{
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Card (

                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            ){

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
                    Icon(
                        imageVector = if (liked == "cau") Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(4.dp),
                                tint = if (liked == "cau") Color.White else Color.White
                    )
                }
                if (ingrediencie != null && nazov != null) {
                    FunIngrediencie(ingrediencie,nazov)
                }
            }
        }
        val vyslednePole = remember { postup.split(Regex("\\d+\\.")) }
        var aktualnaStranka by remember { mutableStateOf(0) }
        val strankovacState = rememberPagerState(pageCount = { vyslednePole.size })

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = strankovacState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                aktualnaStranka = page
                    Box(
                        modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .background(if (page == aktualnaStranka) Color.White else Color.Transparent)
                ) {
                    LazyColumn{

                        item{
                            Text(
                                text = vyslednePole[page],
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun FunIngrediencie(popis_:String, nazov:String) {
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
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start

                    )
                if (expanded) {
                    Text(
                        text = "Ingrediencie",
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start

                    )
                    LazyColumn (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        item{
                            Text(
                                text = popis_
                            )
                        }
                    }

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
