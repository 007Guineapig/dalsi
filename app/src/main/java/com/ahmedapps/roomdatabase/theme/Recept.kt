package com.ahmedapps.roomdatabase.theme
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
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
    Column(

    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            //contentAlignment = Alignment.BottomStart
        ) {


            Card {
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

                //Spacer(modifier = Modifier.height(16.dp))
                if (ingrediencie != null && Nazov != null) {
                    aha(ingrediencie,Nazov)

                }
            }
        }

        LazyColumn {
            item {


                if (postup != null) {
                    Text(
                        text = postup,
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Start
                    )
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
