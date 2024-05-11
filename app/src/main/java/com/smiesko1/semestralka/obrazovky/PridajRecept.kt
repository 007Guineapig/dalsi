package com.smiesko1.semestralka.obrazovky

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.smiesko1.semestralka.pracaSulozenim.ReceptDao
import com.smiesko1.semestralka.pracaSulozenim.ReceptState
import com.smiesko1.semestralka.pracaSulozenim.Receptik
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun PridanieReceptu(dao: ReceptDao,
                    navController: NavController) {
    val textStatePopis = remember { mutableStateOf(TextFieldValue()) }
    val textStateUrl = remember { mutableStateOf(TextFieldValue()) }
    val textStateIngrediencie = remember { mutableStateOf(TextFieldValue()) }
    val textStatePostup = remember { mutableStateOf(TextFieldValue()) }
    val textStateNazov = remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {


        Text(
            text = stringResource(R.string.pridaj),

            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth(),
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
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
        BoxWithConstraints {
            val maxHeight = maxHeight // Get the maximum height from BoxWithConstraints
            TextField(
                value = textStatePostup.value,
                onValueChange = { textStatePostup.value = it },
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Adjust as needed
                label = { Text("Postup") }
            )
        }
        Button(
            onClick = {
                val nazov = textStateNazov.value.text
                val postup = textStatePostup.value.text
                val ingrediencie = textStateIngrediencie.value.text
                val url = textStateUrl.value.text
                val popis = textStatePopis.value.text
                val newRecept = Receptik(
                    nazov = nazov,
                    popis = popis,
                    obrazok = url,
                    ingrediencie = ingrediencie,
                    postup = postup,
                    kategoria = "Some category" // You might need to adjust this
                )

                CoroutineScope(Dispatchers.IO).launch {
                    dao.insert(newRecept)
                }
                navController.popBackStack()
            }
        ) {
            Text("Pridaj Recept")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    MyApp()
}

@Composable
fun MyApp() {
   //PridanieReceptu(state = null,null)
}