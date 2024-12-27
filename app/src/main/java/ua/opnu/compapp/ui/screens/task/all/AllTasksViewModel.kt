package ua.opnu.compapp.ui.screens.task.all

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import ua.opnu.compapp.MyApp
import ua.opnu.compapp.data.model.Task

class AllTasksViewModel : ViewModel() {

    private val repository = MyApp.instance.repository

    val notes: Flow<List<Task>> = repository.tasks

}