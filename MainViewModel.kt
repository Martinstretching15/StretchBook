
package com.example.physionotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.physionotes.data.Patient
import com.example.physionotes.data.TreatmentNote
import com.example.physionotes.repo.Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {
    val patients = repo.patients().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun patient(id: Long) = repo.patient(id).stateIn(viewModelScope, SharingStarted.Lazily, null)
    fun notesFor(id: Long) = repo.notesFor(id).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addPatient(name: String, age: Int?, phone: String?, condition: String?, onDone: (Long)->Unit = {}) {
        viewModelScope.launch {
            val id = repo.addPatient(Patient(name = name, age = age, phone = phone, condition = condition))
            onDone(id)
        }
    }
    fun updatePatient(p: Patient) { viewModelScope.launch { repo.updatePatient(p) } }
    fun deletePatient(p: Patient, onDone: ()->Unit = {}) { viewModelScope.launch { repo.deletePatient(p); onDone() } }
    fun addNote(patientId: Long, content: String) { viewModelScope.launch { repo.addNote(patientId, content) } }
    fun deleteNote(note: TreatmentNote) { viewModelScope.launch { repo.deleteNote(note) } }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                throw IllegalStateException("Use the overloaded create with Application")
            }

            @Suppress("UNCHECKED_CAST")
            fun create(app: Application): MainViewModel {
                return MainViewModel(Repository(app.applicationContext), app)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
val MainViewModel.Companion.Factory: ViewModelProvider.Factory
    get() = object : ViewModelProvider.AndroidViewModelFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val app = getApplication<Application>()
            return MainViewModel(Repository(app.applicationContext), app) as T
        }
    }
