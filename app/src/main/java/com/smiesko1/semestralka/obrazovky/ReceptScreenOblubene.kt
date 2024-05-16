package com.smiesko1.semestralka.obrazovky


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.smiesko1.semestralka.R
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import com.smiesko1.semestralka.presentation.ReceptyVColumne

//SCREEN Oblubene kde sa nachadza searchbar,recepty(ReceptyColumne)
//a 2 tlacidla na spodu obrazovky(home/oblubene)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceptsScreen1(dao: ReceptDao,
    state: ReceptState,
    navController: NavController,

    ) {
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
            text =  stringResource(R.string.liked),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth(),
            fontWeight = FontWeight.ExtraBold,
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
            ReceptyVColumne(pomocna,dao,state,navController,true)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
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
                    imageVector = Icons.Default.Home,
                    contentDescription = "Button 1 Icon"
                )
            }
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(contentColor = Color.Cyan),
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




