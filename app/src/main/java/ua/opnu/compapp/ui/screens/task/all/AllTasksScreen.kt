package ua.opnu.compapp.ui.screens.task.all

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.opnu.compapp.R
import ua.opnu.compapp.data.model.Task
import ua.opnu.compapp.ui.navigation.Graph
import ua.opnu.compapp.ui.theme.AppTypography

@Composable
fun AllTasksScreen(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    viewModel: AllTasksViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val list by viewModel.notes.collectAsState(initial = emptyList())

    if (list.isEmpty()) {
        EmptyList()
    } else {
        TaskssList(list, navController, snackBarHostState, scope)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskssList(
    list: List<Task>,
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        stickyHeader {
            Surface(Modifier.fillParentMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Notes",
                        style = AppTypography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        items(list, key = { item -> item.id }) {
            ListItem(it, snackBarHostState, scope, navController)
        }
    }
}

@Composable
fun EmptyList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.notes_empty_list),
            modifier = Modifier.fillMaxWidth(),
            style = AppTypography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ListItem(
    task: Task,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                val str = "${Graph.VIEW_NOTE}/${task.id}"

                navController.navigate(str)
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = task.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                textAlign = TextAlign.Start,
            )
            IconButton(onClick = { /* TODO: implement change favorite */ }) {
                val vector =
                    if (task.isDone) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                Icon(imageVector = vector, contentDescription = null)
            }

            IconButton(onClick = {
                navController.navigate("${Graph.EDIT_NOTE}/${task.id}")
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = {

                //TODO: implement deletion

                scope.launch {
                    val snackBarResult = snackBarHostState.showSnackbar(
                        message = "Note is deleted",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Short
                    )
                    when (snackBarResult) {
                        SnackbarResult.ActionPerformed -> {
                            //TODO: implement deletion
                        }

                        else -> {
                            Log.d("Snackbar", "Snackbar dismissed")
                        }
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}