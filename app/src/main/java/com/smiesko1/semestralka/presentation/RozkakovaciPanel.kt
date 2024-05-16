package com.smiesko1.semestralka.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smiesko1.semestralka.R

//Panel ktory po kliknuti expanduje a ukaže prislušne informacie(boolean prispôsobuje čí sa zobrazi "ingrediencie")
@Composable
fun FunRozkakovaciPanel(name: String, popis_:String,ingrediencie:Boolean) {
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
                text = name,
                fontSize = 27.sp,
                style = MaterialTheme.typography.headlineMedium.copy(
                   fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                if(ingrediencie){
                    Text(
                        text = "Ingrediencie",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRozkakovaciPanelfalse() {

    FunRozkakovaciPanel("Halászlé","Kotlíkové halászlé - až sa bojím, že sa spustí tradičné komentárové peklo o autenticite :) ",false )
    FunRozkakovaciPanel("Halászlé","Kotlíkové halászlé - až sa bojím, že sa spustí tradičné komentárové peklo o autenticite :) ",true )

}
@Preview(showBackground = true)
@Composable
fun DefaultPreviewRozkakovaciPaneltrue() {

    FunRozkakovaciPanel("Halászlé","Kotlíkové halászlé - až sa bojím, že sa spustí tradičné komentárové peklo o autenticite :) ",true )

}