package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.room.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "task_table")
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val isDone: Boolean
)

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TodoTask>>

    @Insert
    suspend fun insertTask(task: TodoTask)

    @Delete
    suspend fun deleteTask(task: TodoTask)

    @Update
    suspend fun updateTask(task: TodoTask)
}

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao():  TaskDao
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "todo_db"
        ).build()

        val dao = db.taskDao()

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return TodoViewModel(dao) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        val viewModel = ViewModelProvider(this, factory)[TodoViewModel::class.java]

        setContent {
            com.example.todo.ui.theme.TodoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = androidx.navigation.compose.rememberNavController()

                    androidx.navigation.compose.NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            TodoScreen(
                                viewModel = viewModel,
                                onNavigateToAbout = { navController.navigate("about") }
                            )
                        }

                        composable("about") {
                            AboutScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class) // FIX 1: Capital A
@Composable
fun TodoScreen(viewModel: TodoViewModel, onNavigateToAbout: () -> Unit) {
    var currentText by remember { mutableStateOf("") }
    val taskList by viewModel.taskList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODOs") },
                actions = {
                    IconButton(onClick = onNavigateToAbout) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About App"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = currentText,
                    onValueChange = { currentText = it },
                    label = { Text("What's the plan?") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (currentText.isNotBlank()) {
                            viewModel.addTask(currentText)
                            currentText = ""
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("ADD")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(taskList) { task ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = task.isDone,
                                    onCheckedChange = { isChecked ->
                                        viewModel.updateTask(task.copy(isDone = isChecked))
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = task.text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                                )
                            }
                            IconButton(onClick = {
                                viewModel.deleteTask(task)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Task",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}