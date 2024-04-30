package com.ahmedapps.roomdatabase

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ahmedapps.roomdatabase.data.Receptik
import com.ahmedapps.roomdatabase.data.ReceptsDatabase
import com.ahmedapps.roomdatabase.presentation.ReceptsScreen
import com.ahmedapps.roomdatabase.presentation.ReceptsScreen1
import com.ahmedapps.roomdatabase.presentation.ReceptsViewModel
import com.ahmedapps.roomdatabase.theme.Recept
import com.ahmedapps.roomdatabase.theme.Screen
import com.ahmedapps.roomdatabase.theme.Uvod
import com.ahmedapps.roomdatabase.ui.RoomDatabaseTheme
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray


class MainActivity : ComponentActivity() {



    private val viewModel by viewModels<ReceptsViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass: Class<T>): T {
                    return ReceptsViewModel(database.dao) as T
                }
            }
        }
    )

    lateinit var database: ReceptsDatabase
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {


        provideNotesDatabase(applicationContext)
        RoomDatabaseTheme {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {

                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                NavHost(navController=navController,
                    startDestination = Screen.Uvod.rout
                ){
                    composable(Screen.ReceptsScreen.rout){
                        ReceptsScreen(state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent)
                    }

                    composable(
                        Screen.Recept1.rout+ "/{Nazov}/{jpg}/{ingrediencie}/{postup}/{strind}",
                        arguments=listOf(
                            navArgument("Nazov"){type = NavType.StringType },
                            navArgument("jpg"){type = NavType.StringType },
                            navArgument("ingrediencie"){type = NavType.StringType},
                           navArgument("postup"){type = NavType.StringType},
                            navArgument("strind"){type = NavType.StringType}
                            ))
                    {backStackEntry->
                        Recept(navController = navController,backStackEntry=backStackEntry)
                    }
                    composable(Screen.ReceptScreenOblubene.rout+"/{sstring}",
                       arguments = listOf(navArgument("sstring"){type = NavType.StringType})
                    ){backStackEntry->
                        ReceptsScreen1(state = state,
                            navController = navController,backStackEntry=backStackEntry,
                            onEvent = viewModel::onEvent)
                    }
                    composable(Screen.Uvod.rout){
                        Uvod(navController = navController)
                    }
                }
            }
        }
    }
}
    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): ReceptsDatabase{

        database = Room.databaseBuilder(
            context,
            ReceptsDatabase::class.java,
            "receptiks.db"
        )
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        val receptDao = database.dao
                        val receptList: JSONArray =
                            context.resources.openRawResource(R.raw.sample1).bufferedReader().use {
                                JSONArray(it.readText())
                            }

                        receptList.takeIf { it.length() > 0 }?.let { list ->
                            for (index in 0 until list.length()) {
                                val receptObj = list.getJSONObject(index)

                                receptDao.insert(
                                    Receptik(
                                        nazov = receptObj.getString("nazov"),
                                        popis = receptObj.getString("popis"),
                                        obrazok = receptObj.getString("obrazok"),
                                        postup = receptObj.getString("postup"),
                                        ingrediencie = receptObj.getString("ingrediencie"),
                                        kategoria = receptObj.getString("kategoria")
                                    )

                                )

                            }
                        }
                    }
                }


            }).build()
        return database
    }


}


























