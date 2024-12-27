package ua.opnu.compapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ua.opnu.compapp.data.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getNoteById(id: Long): Task

    @Query("DELETE FROM tasks WHERE id = :id")
    fun deleteNoteById(id: Long)

    @Update
    fun updateNote(task: Task)
}