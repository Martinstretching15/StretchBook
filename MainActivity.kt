
package com.example.physionotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.physionotes.ui.screens.AddEditPatientScreen
import com.example.physionotes.ui.screens.PatientDetailScreen
import com.example.physionotes.ui.screens.PatientListScreen
import com.example.physionotes.ui.screens.SettingsScreen
import com.example.physionotes.ui.theme.PhysioNotesTheme
import com.example.physionotes.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhysioNotesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val vm: MainViewModel = viewModel(factory = MainViewModel.Factory)

    NavHost(navController = navController, startDestination = "patients") {
        composable("patients") {
            PatientListScreen(
                vm = vm,
                onAdd = { navController.navigate("addPatient") },
                onOpen = { id -> navController.navigate("patient/$id") },
                onSettings = { navController.navigate("settings") }
            )
        }
        composable("addPatient") {
            AddEditPatientScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                vm = vm
            )
        }
        composable("patient/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: -1
            PatientDetailScreen(
                patientId = id,
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(vm = vm, onBack = { navController.popBackStack() })
        }
    }
}
