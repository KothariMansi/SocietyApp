package com.example.societyapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.societyapp.ui.MainScreen
import com.example.societyapp.ui.MastersScreen
import com.example.societyapp.ui.models.MastersViewModel
import com.example.societyapp.ui.models.SocietyViewModel
import com.example.societyapp.ui.theme.SocietyAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}
        requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        setContent {
            SocietyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                   SocietyApp(
                       mastersViewModel = viewModel(factory = MastersViewModel.factory),
                       societyViewModel = viewModel(factory = SocietyViewModel.factory),
                       activity = this,
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
    activity: ComponentActivity,

) {
    NavHost(navController = navController, startDestination = MyAppScreen.Main.name) {
        composable(MyAppScreen.Main.name){
            MainScreen(
                societyViewModel = societyViewModel,
                add = { navController.navigate(MyAppScreen.Masters.name) },
                activity = activity,
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


