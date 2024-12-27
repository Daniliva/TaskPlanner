package ua.opnu.compapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "task")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val contents: String,
    val isDone: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)