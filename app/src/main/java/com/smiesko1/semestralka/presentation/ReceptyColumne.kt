package com.smiesko1.semestralka.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smiesko1.semestralka.R
import com.smiesko1.semestralka.obrazovky.Screen
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReceptyVColumne(pomocna:String, dao: ReceptDao, state: ReceptState, navController: NavController,boolean:Boolean) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(590.dp)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val indexMax = state.receptiks.size -1

        for (index in 0..indexMax) {

            if (boolean) {

                if ((pomocna.isEmpty() || state.receptiks[index].nazov.contains(pomocna))&& state.receptiks[index].kategoria == "nejde") {
                    item {
                        ReceptItem(
                            dao,
                            state = state,
                            index = index,
                            navController
                        )
                    }
                }
            } else {

                if (pomocna.isEmpty() || state.receptiks[index].nazov.contains(pomocna)) {
                    item {
                        ReceptItem(
                            dao,
                            state = state,
                            index = index,
                            navController
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun ReceptItem(dao: ReceptDao,
               state: ReceptState,
               index: Int,
               navController:NavController
) {
    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .clickable(onClick = {
                navController.navigate(Screen.Recept1.rout + "/$index")
            })
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column{
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(state.receptiks[index].obrazok)
                        .crossfade(true).build(),
                    error = painterResource(R.drawable.error),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.liked),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                ClickableHeartIcon(state,dao,index,
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
            FunRozkakovaciPanel(state.receptiks[index].nazov,state.receptiks[index].popis,false)

        }
    }
}