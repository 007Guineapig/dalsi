package com.ahmedapps.roomdatabase.presentation


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ahmedapps.roomdatabase.R
import com.ahmedapps.roomdatabase.theme.Screen
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceptsScreen1(
    state: ReceptState,
    navController: NavController,backStackEntry: NavBackStackEntry,
    onEvent: (ReceptsEvent) -> Unit
) {
    var g = backStackEntry.arguments?.getString("sstring")


    var pf= g?.split(",")?.toTypedArray()

    var text by remember { mutableStateOf("")}
    var active by remember { mutableStateOf(false)}
    var pomocna = ""
    var items = remember {
        mutableStateListOf<String>()

    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        Text(
            text = "Liked",
            modifier = Modifier
                //.padding(10.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.ExtraBold,
           // fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
            //fontFamily = FontFamily()

        )

        SearchBar(modifier=Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                items.add(text)
                text = ""
                active = false

            },
            active = active,
            onActiveChange = {active = it},
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,contentDescription = "Search Icon")},
            trailingIcon = {
                if(active) {
                    Icon(
                        modifier = Modifier.clickable(){
                            if(text.isNotEmpty()){
                                text = ""
                            } else{ active = false}

                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }

        ) {
            items.forEach {
                Row(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .clickable(onClick = { text = it })
                )
                {
                    Icon(
                        Icons.Default.History,
                        contentDescription = "History Icon",
                        modifier = Modifier.padding(end = 10.dp)

                    )
                    Text(text = it)
                    pomocna = it
                }
            }
        }




        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .height(590.dp)
                .padding(8.dp),

            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.receptiks.size) { index ->
                if (pf != null) {
                    if ((pomocna.isEmpty() || state.receptiks[index].nazov.contains(pomocna))&& pf.get(index) =="cau") {
                        ReceptItem1(
                            state = state,
                            index = index,
                            onEvent = onEvent,
                            navController
                        )
                    }
                }

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp), // Adjust the vertical padding here
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.ReceptsScreen.rout)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home, // Replace "YourIcon" with the desired icon
                    contentDescription = "Button 1 Icon"
                )
            }

            Button(
                onClick = {
                    // Handle button 2 click
                },
                colors = ButtonDefaults.buttonColors(contentColor = Color.Cyan),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp, // Replace "YourIcon" with the desired icon
                    contentDescription = "Button 1 Icon"
                )
            }
        }

    }

}



@Composable
fun ReceptItem1(
    state: ReceptState,
    index: Int,
    onEvent: (ReceptsEvent) -> Unit,
    navController:NavController
) {
    val nazov_ = state.receptiks[index].nazov //stringResource(affirmation.stringResourceId)
    val vyzor_ = URLEncoder.encode(state.receptiks[index].obrazok, "UTF-8")
    val ingrediencie = URLEncoder.encode(state.receptiks[index].ingrediencie, "UTF-8")
    val postup = URLEncoder.encode(state.receptiks[index].postup, "UTF-8")

    val popis_ = state.receptiks[index].popis
    val vyzor = state.receptiks[index].obrazok
    val ingrediencie_ = state.receptiks[index].ingrediencie
    val postup_ = postup



    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),

        modifier = Modifier
            .clickable(onClick = { navController.navigate(Screen.Recept1.rout + "/$nazov_/$vyzor_/$ingrediencie/$postup_/cau") }) // Add clickable modifier here
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column{
            Box{
            Image(
                painter = rememberImagePainter(
                    data = vyzor,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        // Adjust size as needed
                        .padding(16.dp)
                        .size(48.dp), // Add padding if necessary
                    tint = Color.White
                )

            }
            CardContent(nazov_,popis_)

        }

    }
}

@Composable
private fun CardContent(name: String,popis_:String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
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
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = popis_
                )
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





