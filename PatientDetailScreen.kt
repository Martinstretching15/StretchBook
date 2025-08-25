
package com.example.physionotes.ui.screens

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.physionotes.data.Patient
import com.example.physionotes.data.TreatmentNote
import com.example.physionotes.viewmodel.MainViewModel

@Composable
fun PatientDetailScreen(patientId: Long, vm: MainViewModel, onBack: ()->Unit) {
    val patientState = vm.patient(patientId).collectAsState()
    val notes = vm.notesFor(patientId).collectAsState()
    var newNote by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(patientState.value?.name ?: "Patient") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } },
                actions = {
                    val p = patientState.value
                    if (p != null) {
                        IconButton(onClick = { vm.deletePatient(p, onBack) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete patient")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            val p = patientState.value
            if (p != null) {
                PatientInfo(p)
                Spacer(Modifier.height(16.dp))
                Text("Treatment Notes", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = newNote,
                    onValueChange = { newNote = it },
                    label = { Text("Add a note (progress, exercises, pain scale...)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = { if (newNote.isNotBlank()) { vm.addNote(p.id, newNote.trim()); newNote = "" } }) {
                    Text("Add Note")
                }
                Spacer(Modifier.height(16.dp))
                LazyColumn {
                    items(notes.value) { n ->
                        NoteItem(n, onDelete = { vm.deleteNote(n) })
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun PatientInfo(p: Patient) {
    ElevatedCard {
        Column(Modifier.padding(16.dp)) {
            Text("Name: ${p.name}")
            if (p.age != null) Text("Age: ${p.age}")
            if (!p.phone.isNullOrBlank()) Text("Phone: ${p.phone}")
            if (!p.condition.isNullOrBlank()) Text("Condition: ${p.condition}")
        }
    }
}

@Composable
private fun NoteItem(n: TreatmentNote, onDelete: ()->Unit) {
    val date = remember(n.timestamp) { DateFormat.getDateTimeInstance().format(n.timestamp) }
    ListItem(
        headlineContent = { Text(date) },
        supportingContent = { Text(n.content) },
        trailingContent = {
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, contentDescription = "Delete") }
        }
    )
}
