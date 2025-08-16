package com.sandeep.personalnotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sandeep.personalnotes.ui.NoteAddEditScreen
import com.sandeep.personalnotes.ui.NoteViewModel
import com.sandeep.personalnotes.ui.NotepadYellow
import com.sandeep.personalnotes.ui.NotesListScreen
import com.sandeep.personalnotes.ui.PlatformPdfViewer
import com.sandeep.personalnotes.ui.Screen
import com.sandeep.personalnotes.ui.bottomNavScreens
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<NoteViewModel>()
    Scaffold(
        modifier = Modifier.background(NotepadYellow),
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                bottomNavScreens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = { navController.navigate(screen.route) },
                        icon = {
                            Icon(
                                imageVector = when (screen) {
                                    is Screen.Notes -> Icons.Default.Notes
                                    is Screen.PdfViewer -> Icons.Default.PictureAsPdf
                                    is Screen.NoteEdit -> Icons.Default.Home
                                },
                                contentDescription = screen.route
                            )
                        },
                        label = { Text(screen.route.replace("_", " ").capitalize()) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Notes.route) {
                NotesListScreen(
                    viewModel = viewModel,
                    onNoteClick = { navController.popBackStack() },
                    onAddNote = { navController.navigate("note_edit?noteId=-1") }
                )
            }
            composable(
                "note_edit?noteId={noteId}",
                arguments = listOf(navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong("noteId")?.takeIf { it != -1L }
                NoteAddEditScreen(
                    viewModel = viewModel,
                    onDone = { navController.popBackStack() }
                )
            }
            composable(Screen.PdfViewer.route) {
                PlatformPdfViewer(viewModel)
            }
        }
    }


}