
package com.example.physionotes.repo

import android.content.Context
import com.example.physionotes.data.*
import kotlinx.coroutines.flow.Flow

class Repository(context: Context) {
    private val db = DbModule.db(context)
    private val patients = db.patientDao()
    private val notes = db.noteDao()

    fun patients(): Flow<List<Patient>> = patients.observeAll()
    fun patient(id: Long): Flow<Patient?> = patients.observeById(id)
    fun notesFor(patientId: Long): Flow<List<TreatmentNote>> = notes.observeForPatient(patientId)

    suspend fun addPatient(p: Patient): Long = patients.insert(p)
    suspend fun updatePatient(p: Patient) = patients.update(p)
    suspend fun deletePatient(p: Patient) = patients.delete(p)

    suspend fun addNote(patientId: Long, content: String) =
        notes.insert(TreatmentNote(patientId = patientId, content = content))

    suspend fun deleteNote(note: TreatmentNote) = notes.delete(note)
}
