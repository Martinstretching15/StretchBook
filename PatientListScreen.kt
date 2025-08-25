
package com.example.physionotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.physionotes.data.Patient
import com.example.physionotes.ui.components.Icons
import com.example.physionotes.ui.components.TopBar
import com.example.physionotes.viewmodel.MainViewModel

@Composable
fun PatientListScreen(vm: MainViewModel, onAdd: ()->Unit, onOpen: (Long)->Unit, onSettings: ()->Unit) {
    val patients = vm.patients.collectAsState()
    Scaffold(
        topBar = {
            TopBar("PhysioNotes", actions = {
                IconButton(onClick = onSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, contentDescription = "Add") }
        }
    ) { padding ->
        if (patients.value.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No patients yet. Tap + to add.")
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(padding)) {
                items(patients.value) { p ->
                    ListItem(
                        headlineContent = { Text(p.name) },
                        supportingContent = { Text(p.condition ?: "") },
                        trailingContent = { Text(p.phone ?: "") },
                        modifier = Modifier.fillMaxWidth().clickable { onOpen(p.id) }
                    )
                    Divider()
                }
            }
        }
    }
}
