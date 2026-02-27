package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

        setContent {
            TodoScreen(dao = dao)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // FIX 1: Capital A
@Composable
fun TodoScreen(dao: TaskDao) {
    var currentText by remember { mutableStateOf("") }
    val taskList by dao.getAllTasks().collectAsState(initial = emptyList())
    val coroutinesScope = rememberCoroutineScope()

    // FIX 2: Parentheses () instead of curly braces {} for the Scaffold parameter
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODOs") },
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
                            val textToSave = currentText
                            coroutinesScope.launch {
                                dao.insertTask(TodoTask(text = textToSave, isDone = false))
                            }
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
                                        coroutinesScope.launch {
                                            dao.updateTask(task.copy(isDone = isChecked))
                                        }
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
                                coroutinesScope.launch {
                                    dao.deleteTask(task)
                                }
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