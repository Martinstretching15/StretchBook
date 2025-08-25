
package com.example.physionotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.physionotes.viewmodel.MainViewModel

@Composable
fun AddEditPatientScreen(onBack: ()->Unit, onSaved: ()->Unit, vm: MainViewModel) {
    var name by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }

    Scaffold(
        topBar = { SmallTopAppBar(title = { Text("Add Patient") }) }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = ageText, onValueChange = { ageText = it.filter { ch -> ch.isDigit() } }, label = { Text("Age") }, keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = condition, onValueChange = { condition = it }, label = { Text("Condition / Dx") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                val age = ageText.toIntOrNull()
                vm.addPatient(name.trim(), age, phone.ifBlank { null }, condition.ifBlank { null }) {
                    onSaved()
                }
            }, enabled = name.isNotBlank()) {
                Text("Save")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onBack) { Text("Cancel") }
        }
    }
}
