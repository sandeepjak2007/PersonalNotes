package com.sandeep.personalnotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import com.sandeep.personalnotes.ui.NoteAddEditScreen
import com.sandeep.personalnotes.ui.NoteViewModel
import com.sandeep.personalnotes.ui.NotepadAccent
import com.sandeep.personalnotes.ui.NotepadLine
import com.sandeep.personalnotes.ui.NotesListScreen
import com.sandeep.personalnotes.ui.PdfScreen
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
        bottomBar = {
            NavigationBar(
                containerColor = NotepadAccent
            ) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                bottomNavScreens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = { navController.navigate(screen.route) },
                        icon = {
                            Icon(
                                imageVector = when (screen) {
                                    is Screen.Notes -> Icons.AutoMirrored.Filled.Notes
                                    is Screen.PdfViewer -> Icons.Default.PictureAsPdf
                                    is Screen.NoteEdit -> Icons.Default.Home
                                },
                                contentDescription = screen.route
                            )
                        },
                        label = {
                            Text(
                                screen.route.replace("_", " ")
                                    .replaceFirstChar { ch -> ch.uppercase() })
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(NotepadLine)
        ) {
            composable(Screen.Notes.route) {
                NotesListScreen(
                    viewModel = viewModel,
                    onNoteClick = { id -> navController.navigate("note_edit?noteId=$id") },
                    onAddNote = { navController.navigate("note_edit?noteId=-1") }
                )
            }
            composable(
                "note_edit?noteId={noteId}",
                arguments = listOf(navArgument(name = "noteId") {
                    type = NavType.LongType
                    defaultValue = -1L
                })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("noteId")?.takeIf { it != -1L }
                println("Note ID: $id")
                NoteAddEditScreen(
                    noteId = id,
                    viewModel = viewModel,
                    onDone = { navController.navigate(Screen.Notes.route) }
                )
            }
            composable(Screen.PdfViewer.route) {
//                val state =
//                    rememberWebViewStateWithHTMLData("https://qa.pilloo.ai/GeneratedPDF/Companies/202/2025-2026/DL.pdf", mimeType = "application/pdf")
//                if (state.loadingState is LoadingState.Loading) {
//                    CircularProgressIndicator(
//                        modifier = Modifier.size(20.dp),
//                        color = Color.Blue,
//                        strokeWidth = 4.dp,
//                        trackColor = Color.LightGray,
//                        strokeCap = StrokeCap.Round
//                    )
//                }
//                WebView(
//                    state = state,
//                    navigator = rememberWebViewNavigator(),
//                    modifier = Modifier.fillMaxSize()
//                )
                PdfScreen(viewModel, "https://qa.pilloo.ai/GeneratedPDF/Companies/202/2025-2026/DL.pdf")
            }
        }
    }


}