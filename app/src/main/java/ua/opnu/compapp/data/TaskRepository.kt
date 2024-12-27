package ua.opnu.compapp.data

import kotlinx.coroutines.flow.Flow
import ua.opnu.compapp.data.database.TaskDao
import ua.opnu.compapp.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val tasks: Flow<List<Task>> = taskDao.getAllNotes()

    suspend fun add(task: Task) = taskDao.addNote(task)

    suspend fun getById(id: Long) = taskDao.getNoteById(id)
    suspend fun update(it: Task) = taskDao.updateNote(it)

}