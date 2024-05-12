package com.smiesko1.semestralka.obrazovky
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.smiesko1.semestralka.R
import com.smiesko1.semestralka.presentation.FunRozkakovaciPanel

import java.net.URLDecoder




@SuppressLint("SuspiciousIndentation", "AutoboxingStateCreation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Recept(backStackEntry: NavBackStackEntry){

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

                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current).data(decodedUrl)
                            .crossfade(true).build(),
                        error = painterResource(R.drawable.error),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = stringResource(R.string.liked),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                            .height(250.dp)
                    )
                    Icon(
                        imageVector = if (liked == "cau") Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(4.dp)
                    )
                }
                if (ingrediencie != null && nazov != null) {
                    FunRozkakovaciPanel(nazov,ingrediencie,true)
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
                        .background(Color.Transparent)
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
