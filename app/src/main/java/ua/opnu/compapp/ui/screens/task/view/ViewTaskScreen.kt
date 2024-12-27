package ua.opnu.compapp.ui.screens.task.view

import androidx.compose.runtime.Composable
import ua.opnu.compapp.ui.components.ScreenLabel

@Composable
fun ViewTaskScreen(id: Long) {
    ScreenLabel(text = "VIEW NOTE WITH ID $id")
}