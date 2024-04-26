package com.ahmedapps.roomdatabase.theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LastScreen(navController: NavHostController){

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LastOkno",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick =
        {
            navController.popBackStack()
            //navController.navigate(Screen.Menu.rout)
            navController.popBackStack();


        }) {
            Text(
                text = "Go to Last",
            )
        }
    }
}
