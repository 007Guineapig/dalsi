package com.smiesko1.semestralka.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smiesko1.semestralka.R
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Panel ktory po kliknuti expanduje a ukaže prislušne informacie(boolean prispôsobuje čí sa zobrazi "ingrediencie")
@Composable
fun FunRozkakovaciPanel(dao:ReceptDao?,index:Int,state: ReceptState,edit: Boolean,ingrediencie:Boolean) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val textStatePopis = remember { mutableStateOf(state.receptiks[index].popis) }
    val textStateUrl = remember { mutableStateOf(state.receptiks[index].obrazok) }
    val textStateIngrediencie = remember { mutableStateOf(state.receptiks[index].ingrediencie) }
    val textStateNazov = remember { mutableStateOf(state.receptiks[index].nazov) }

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
                text = state.receptiks[index].nazov,
                fontSize = 27.sp,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                if(edit){

                    TextField(
                        value = textStateNazov.value,
                        onValueChange = { textStateNazov.value = it },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Nazov") }
                    )
                    TextField(
                        value = textStatePopis.value,
                        onValueChange = { textStatePopis.value = it },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Popis") }
                    )
                    TextField(
                        value = textStateUrl.value,
                        onValueChange = { textStateUrl.value = it },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Url:Obrazok") }

                    )

                    TextField(
                        value = textStateIngrediencie.value,
                        onValueChange = { textStateIngrediencie.value = it },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .height(150.dp)
                            .fillMaxWidth(),

                        label = { Text("Ingrediencie") }
                    )
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            var nz = state.receptiks[index].nazov
                            var popis_ =textStatePopis.value
                            var nazov_ =textStateNazov.value
                            var url_=textStateUrl.value
                            var ingrediencie_=textStateIngrediencie.value
                            CoroutineScope(Dispatchers.IO).launch {
                                dao?.updateAll(popis_,nazov_,url_,ingrediencie_,state.receptiks[index].id)

                            }
                        }
                    ) {
                        Text("Update")
                    }

                }

                    if(!ingrediencie){
                        Text(
                            text = "Ingrediencie",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                    Text(
                        text = state.receptiks[index].popis
                    )
            }
                if(ingrediencie){
                    Text(
                        text = state.receptiks[index].ingrediencie
                    )
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

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRozkakovaciPanelfalse() {

    FunRozkakovaciPanel(null,0,ReceptState(),false,false )
    FunRozkakovaciPanel(null,0,ReceptState(),false,true )

}
@Preview(showBackground = true)
@Composable
fun DefaultPreviewRozkakovaciPaneltrue() {

    FunRozkakovaciPanel(null,0,ReceptState(),false,true )

}