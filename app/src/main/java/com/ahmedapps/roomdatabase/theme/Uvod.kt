package com.ahmedapps.roomdatabase.theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ahmedapps.roomdatabase.R


@Composable
fun Uvod(navController: NavHostController,
    //onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.image11),
            contentDescription = "stringResource()",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Crop

        )
        Button(
            colors = ButtonDefaults.buttonColors(Color.Black.copy(alpha = 0.5f)),
            //colors = ButtonDefaults.buttonColors(Color.Red.copy(alpha = 0.5f),

            modifier = Modifier

                .align(Alignment.Center)
                .height(100.dp)
                .width(300.dp)

                .clip(RoundedCornerShape(8.dp))

                .padding(bottom = 16.dp),
            onClick = {navController.navigate(Screen.ReceptsScreen.rout)}

        ) {
            Row {


            Text(
                        text = "Lets COOk",
                color = Color.White,
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Serif,

                    fontSize = 40.sp
                )
            )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier

                        .padding(start = 16.dp, top = 16.dp)
                )
            }




        }

    }
}