package com.smiesko1.semestralka.obrazovky

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smiesko1.semestralka.R
import com.smiesko1.semestralka.pracaSulozenim.PreferencesManager
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import com.smiesko1.semestralka.presentation.ClickableHeartIcon
import com.smiesko1.semestralka.presentation.FunRozkakovaciPanel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceptsScreen(preferences: PreferencesManager,dao: ReceptDao,
                  state: ReceptState,
                  navController: NavController,
) {
    val context = LocalContext.current
    //val preferencesManager = remember { PreferencesManager(context) }
    val preferencesManager = preferences
    var text by remember { mutableStateOf("")}
    var active by remember { mutableStateOf(false)}
    var pomocna = ""
    val items = remember {
        mutableStateListOf<String>()
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            Text(
                text = stringResource(R.string.menu),
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable(onClick =
                    { navController.navigate(Screen.PridajRecept.rout) }

                    )
                    .fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
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
                            modifier = Modifier.clickable{
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
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()){
                        LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.receptiks.size) { index ->
                            if (pomocna.isEmpty() || state.receptiks[index].nazov.contains(pomocna)) {
                            ReceptItem(dao,
                                preferencesManager,
                                state = state,
                                index = index,
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
                        onClick = {},
                            colors = ButtonDefaults.buttonColors(contentColor = Color.Cyan),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                            ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Button 1 Icon"
                            )
                        }
                        Button(
                        onClick = {

                            navController.navigate(Screen.ReceptScreenOblubene.rout)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                        ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Button 1 Icon"
                        )
                }
            }
        }
    }



@Composable
fun ReceptItem(dao: ReceptDao,
    preferencesManager: PreferencesManager,
    state: ReceptState,
    index: Int,
    navController:NavController
) {

    val nazov_ = state.receptiks[index].nazov //stringResource(affirmation.stringResourceId)
    val vyzor_ = URLEncoder.encode(state.receptiks[index].obrazok, "UTF-8")
    val ingrediencie = URLEncoder.encode(state.receptiks[index].ingrediencie, "UTF-8")
    val postup = URLEncoder.encode(state.receptiks[index].postup, "UTF-8")
    val popis_ = state.receptiks[index].popis
    val vyzor = state.receptiks[index].obrazok
    val postup_ = postup

    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .clickable(onClick = {
                val liknuteVoStringu = preferencesManager.getDataAsString("myKey")
                val liknuteVoPole = liknuteVoStringu
                    ?.split(",")
                    ?.toTypedArray()
                var jeLiknutyTento = liknuteVoPole?.get(index)
                if (jeLiknutyTento == "" || jeLiknutyTento == null) {
                    jeLiknutyTento = "ahoj"
                }
                navController.navigate(Screen.Recept1.rout + "/$nazov_/$vyzor_/$ingrediencie/$postup_/$jeLiknutyTento")
            })
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column{
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(vyzor)
                        .crossfade(true).build(),
                    error = painterResource(R.drawable.error),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.liked),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                        ClickableHeartIcon(preferencesManager,index,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(48.dp),
                        onHeartClicked = {
                    }
                )


                var showDialog by remember { mutableStateOf(false) }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Confirm Deletion") },
                        text = { Text("Are you sure you want to delete this item?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    // Perform delete operation
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dao.deleteById(state.receptiks[index].nazov)
                                    }
                                }
                            ) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog = false }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
                }



                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
                    .align(Alignment.TopEnd)
                    .clickable{

                        showDialog = true

                    }

                )

            }
            FunRozkakovaciPanel(nazov_, popis_,false)
        }
    }
}













