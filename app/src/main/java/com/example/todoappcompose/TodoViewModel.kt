package com.example.todoappcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    val todos = mutableStateListOf<Todo>()

    init {
        viewModelScope.launch {
            delay(500)
            todos.add(Todo(title = "Learn TS", description = "Interview Prep"))
            todos.add(Todo(title = "Complete Flutter App V2", isCompleted = false))
            todos.add(Todo(title = "Refer DSA"))
        }
    }

    fun addTodo(title: String, description: String = "") {
        if (title.isNotBlank()) {
            val newTodo = Todo(title = title, description = description)
            todos.add(newTodo)
            println("Added new todo: $newTodo, Total Todos: ${todos.size}")
        }
    }

    fun toggleTodoCompletion(todo: Todo) {
        val index = todos.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            val updatedTodo = todos[index].copy(isCompleted = !todos[index].isCompleted)
            todos[index] = updatedTodo
            println("Toggled completion for: $updatedTodo")
        }
    }

    fun deleteTodo(todo : Todo) {
        val removed = todos.remove(todo)
        if (removed) {
            println("Removed todo: $todo. Reamining Todos: ${todos.size}")
        }
    }
}