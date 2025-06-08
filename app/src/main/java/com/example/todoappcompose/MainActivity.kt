// src/main/java/com/example.todoappcompose/MainActivity.kt
package com.example.todoappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn // Import LazyColumn
import androidx.compose.foundation.lazy.items // Import items for LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoappcompose.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoAppScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppScreen(
    todoViewModel: TodoViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Todo App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // For testing, let's keep adding dummy data.
                // Later, this will open a dialog to add a real todo.
                todoViewModel.addTodo("New Dummy Todo", "Added from FAB")
            }) {
                Icon(Icons.Filled.Add, "Add new todo")
            }
        }
    ) { paddingValues ->
        // This is the main content area of our app
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply padding from Scaffold
            color = MaterialTheme.colorScheme.background // Use background color for the list area
        ) {
            // Use LazyColumn for an efficient scrollable list
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Use items to loop through the list of todos from our ViewModel
                items(todoViewModel.todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggleComplete = { toggledTodo ->
                            // When TodoItem's checkbox or row is clicked, call ViewModel function
                            todoViewModel.toggleTodoCompletion(toggledTodo)
                        }
                    )
                }

                // Optional: Add a header or footer to the list if needed
                item {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "Total Todos: ${todoViewModel.todos.size}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    TodoAppTheme {
        TodoAppScreen()
    }
}