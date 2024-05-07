package com.example.societyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.societyapp.ui.MainScreen
import com.example.societyapp.ui.MastersScreen
import com.example.societyapp.ui.data.SocietyDatabase
import com.example.societyapp.ui.models.MastersViewModel
import com.example.societyapp.ui.models.SocietyViewModel
import com.example.societyapp.ui.theme.SocietyAppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy { Room.databaseBuilder(
        applicationContext,
        SocietyDatabase::class.java, "Society.db"
    ).build()

    }
    private val viewModel by viewModels<MastersViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return MastersViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocietyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   SocietyApp(
                       mastersViewModel = viewModel
                   )
                }
            }
        }
    }
}

enum class MyAppScreen{
    Masters,
    Main
}

@Composable
fun SocietyApp(
    navController: NavHostController = rememberNavController(),
    societyViewModel: SocietyViewModel = viewModel(),
    mastersViewModel: MastersViewModel,
    
) {
    NavHost(navController = navController, startDestination = MyAppScreen.Main.name) {
        composable(MyAppScreen.Main.name){
            MainScreen(
                societyViewModel = societyViewModel,
                add = { navController.navigate(MyAppScreen.Masters.name) }
            )
        }

        composable(MyAppScreen.Masters.name) {
            MastersScreen(
                mastersViewModel = mastersViewModel,
                onBackPress = { navController.popBackStack() }
                )
        }

    }
}


