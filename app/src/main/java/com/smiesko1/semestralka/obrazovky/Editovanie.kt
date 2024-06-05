package com.smiesko1.semestralka.obrazovky

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.smiesko1.semestralka.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import com.smiesko1.semestralka.pracaSulozenim.Receptik
import com.smiesko1.semestralka.presentation.FunRozkakovaciPanel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//Screen kde použivatel môže editovať svoje recepty
@Composable
fun EditovanieReceptu(dao: ReceptDao?,state: ReceptState) {

    LazyColumn(
        modifier = Modifier
            .background(Color.White )
            .fillMaxWidth()
            .fillMaxHeight()
            .height(590.dp)
            .padding(8.dp)

    ) {
        val indexMax = state.receptiks.size -1

        for (index in 0..indexMax) {

            item{
                FunRozkakovaciPanel(dao,index,state,true,false)
            }

        }
    }
}

