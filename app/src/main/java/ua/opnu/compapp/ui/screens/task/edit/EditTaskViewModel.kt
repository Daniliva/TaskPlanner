package ua.opnu.compapp.ui.screens.task.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.opnu.compapp.MyApp
import ua.opnu.compapp.data.model.Task
import java.time.LocalDateTime

class EditTaskViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(EditNoteUiState())
    val uiState: StateFlow<EditNoteUiState> = _uiState

    private val repository = MyApp.instance.repository

    private var editTask: Task? = null

    fun setTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun setText(text: String) {
        _uiState.update {
            it.copy(text = text)
        }
    }

    fun getNoteById(id: Long) {

        if (editTask != null)
            return

        if (id == -1L) {
            _uiState = MutableStateFlow(EditNoteUiState())
            return
        } else {

            viewModelScope.launch(Dispatchers.IO) {
                editTask = repository.getById(id)
                editTask?.let {
                    setTitle(it.title)
                    setText(it.contents)
                }
            }
        }
    }

    fun addNote() {
        val date = LocalDateTime.now()
        val task = Task(
            title = _uiState.value.title,
            contents = _uiState.value.text,
            isDone = false,
            createdAt = date,
            updatedAt = date
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.add(task)
        }

    }

    fun clearForm() {
        _uiState.update {
            it.copy(title = "", text = "")
        }
    }

    fun updateNote() {
        val note = editTask?.copy(
            title = _uiState.value.title,
            contents = _uiState.value.text,
            updatedAt = LocalDateTime.now()
        )

        note?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.update(it)
            }
        }

    }

}

data class EditNoteUiState(
    val title: String = "",
    val text: String = ""
)