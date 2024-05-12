package com.smiesko1.semestralka.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.smiesko1.semestralka.pracaSulozenim.PreferencesManager

@Composable
fun ClickableHeartIcon(preferencesManager: PreferencesManager,
                       index: Int,
                       modifier: Modifier = Modifier,
                       onHeartClicked: () -> Unit
) {
    var data by remember { mutableStateOf(preferencesManager.getData("myKey", emptyArray())) }
    var srdceKliknute by remember { mutableStateOf(false) }
    LaunchedEffect(preferencesManager) {
        data = preferencesManager.getData("myKey", Array(10) { "" })
    }
    Icon(
        imageVector = if (srdceKliknute || (data.getOrNull(index) == "cau")) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
        modifier = modifier.clickable {
            val newData = data.toMutableList()
            if (data.isEmpty() || data.getOrNull(index) == null || data[index] != "cau") {
                newData[index] = "cau"
            } else {
                newData[index] = ""
            }
            preferencesManager.saveData("myKey", newData.toTypedArray())
            data = newData.toTypedArray()
            srdceKliknute = newData[index] == "cau"
            onHeartClicked()

        }
    )
}