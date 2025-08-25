
package com.example.physionotes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int? = null,
    val phone: String? = null,
    val condition: String? = null
)

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["id"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("patientId")]
)
data class TreatmentNote(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val patientId: Long,
    val timestamp: Long = Instant.now().toEpochMilli(),
    val content: String
)

@Dao
interface PatientDao {
    @Query("SELECT * FROM patients ORDER BY name ASC")
    fun observeAll(): Flow<List<Patient>>

    @Query("SELECT * FROM patients WHERE id = :id LIMIT 1")
    fun observeById(id: Long): Flow<Patient?>

    @Insert
    suspend fun insert(patient: Patient): Long

    @Update
    suspend fun update(patient: Patient)

    @Delete
    suspend fun delete(patient: Patient)
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE patientId = :patientId ORDER BY timestamp DESC")
    fun observeForPatient(patientId: Long): Flow<List<TreatmentNote>>

    @Insert
    suspend fun insert(note: TreatmentNote): Long

    @Delete
    suspend fun delete(note: TreatmentNote)
}

@Database(entities = [Patient::class, TreatmentNote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun noteDao(): NoteDao
}
