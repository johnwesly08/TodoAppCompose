package com.example.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class TodoViewModel (private val dao: TaskDao) : ViewModel() {
    val taskList: StateFlow<List<TodoTask>> = dao.getAllTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTask(taskText: String) {
        viewModelScope.launch {
            dao.insertTask(TodoTask(text = taskText, isDone = false))
        }
    }

    fun updateTask(task: TodoTask) {
        viewModelScope.launch {
            dao.updateTask(task)
        }
    }

    fun deleteTask(task: TodoTask) {
        viewModelScope.launch {
            dao.deleteTask(task)
        }
    }
}
