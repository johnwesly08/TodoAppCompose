package com.example.todoappcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoItem(
    todo: Todo, // The Todo item to display
    onToggleComplete: (Todo) -> Unit, // Callback for when the checkbox is clicked
    modifier: Modifier = Modifier // Allow external modifiers
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Add some vertical spacing between cards
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant // A slightly different background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleComplete(todo) } // Make the whole row clickable to toggle
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox for completion status
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { _ -> onToggleComplete(todo) } // Delegate to the callback
            )
            Spacer(Modifier.width(8.dp)) // Space between checkbox and text

            // Todo Title and Description
            Column(modifier = Modifier.weight(1f)) { // Takes up available width
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null, // Strikethrough if completed
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (todo.description.isNotBlank()) { // Only show description if it's not empty
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f) // Slightly faded
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoItem() {
    // Import your theme here if needed for preview
    com.example.todoappcompose.ui.theme.TodoAppTheme {
        Column {
            TodoItem(
                todo = Todo(title = "Buy groceries", description = "Milk, eggs, bread", isCompleted = false),
                onToggleComplete = {} // Dummy callback for preview
            )
            TodoItem(
                todo = Todo(title = "Finish report", isCompleted = true),
                onToggleComplete = {}
            )
            TodoItem(
                todo = Todo(title = "Call mom"),
                onToggleComplete = {}
            )
        }
    }
}