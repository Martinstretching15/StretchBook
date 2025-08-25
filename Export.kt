
package com.example.physionotes.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.physionotes.data.Patient
import com.example.physionotes.data.TreatmentNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun MainViewModel.exportAllToCsv(uri: Uri) {
    viewModelScope.launch {
        val context = getApplication<android.app.Application>().applicationContext
        val resolver = context.contentResolver
        val ps = patients.first()
        val notesByPatient = ps.associate { p -> p.id to notesFor(p.id).first() }
        withContext(Dispatchers.IO) {
            resolver.openOutputStream(uri)?.bufferedWriter().use { out ->
                if (out != null) {
                    out.appendLine("PATIENTS")
                    out.appendLine("id,name,age,phone,condition")
                    for (p in ps) {
                        out.appendLine("${p.id},${p.name},${p.age ?: ""},${p.phone ?: ""},${p.condition ?: ""}")
                    }
                    out.appendLine()
                    out.appendLine("NOTES")
                    out.appendLine("id,patientId,timestamp,content")
                    for ((_, notes) in notesByPatient) {
                        for (n in notes) {
                            val safeContent = n.content.replace('\n', ' ').replace(',', ';')
                            out.appendLine("${n.id},${n.patientId},${n.timestamp},${safeContent}")
                        }
                    }
                    out.flush()
                }
            }
        }
    }
}
