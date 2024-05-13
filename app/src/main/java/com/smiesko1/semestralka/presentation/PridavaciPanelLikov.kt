package com.smiesko1.semestralka.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ClickableHeartIcon(
    state: ReceptState,
    dao: ReceptDao,
    index: Int,
    modifier: Modifier = Modifier,
    onHeartClicked: () -> Unit
) {
    var srdceKliknute by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val isClicked = dao.isClicked(state.receptiks[index].nazov)
        srdceKliknute = isClicked > 0
    }

    Icon(
        imageVector = if (srdceKliknute) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
        modifier = modifier.clickable {
            val nazov = state.receptiks[index].nazov
            srdceKliknute = !srdceKliknute

            CoroutineScope(Dispatchers.IO).launch {
                dao.update(if (srdceKliknute) "nejde" else "", nazov)
                withContext(Dispatchers.Main) {
                    onHeartClicked()
                }
            }
        }
    )

    DisposableEffect(state.receptiks[index].nazov) {

        CoroutineScope(Dispatchers.IO).launch {
            val isClicked = dao.isClicked(state.receptiks[index].nazov)
            srdceKliknute = isClicked>0
        }

        onDispose { /* Clean up if needed */ }
    }
}