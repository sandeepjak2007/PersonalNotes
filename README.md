# Personal Notes Compose KMP

A Kotlin Multiplatform (KMP) Personal Notes application featuring a modern Jetpack Compose UI, multiplatform PDF viewing, and robust state management.  
The project leverages key multiplatform libraries for a scalable, testable architecture.

---

## Features

- **Add/Edit Notes:** Create and edit notes with a title, HTML content, and date picker.
- **Notes List Screen:** Browse all notes, see summaries, and click to view/edit.
- **PDF Viewer:** View PDF files in-app (platform-appropriate).
- **Modern Compose UI:** Material3 components, animations, reactive flows.
- **KMP Architecture:** Shared business logic and ViewModel; platform-specific UI integration.
- **Koin-powered Dependency Injection:** Easy to wire up anywhere.

---

## Libraries & Versions

All versions and artifact aliases are defined in [libs.versions.toml].

### Core Libraries

| Technology       | Artifact                   | Version     |
|------------------|---------------------------|-------------|
| Kotlin           | `kotlin`                  | 2.2.10      |
| Compose Multiplatform | `composeMultiplatform`      | 1.8.2       |
| AndroidX Activity| `androidx-activity`         | 1.10.1      |
| AppCompat        | `androidx-appcompat`        | 1.7.1       |
| Core KTX         | `androidx-core-ktx`         | 1.16.0      |
| Lifecycle Compose| `androidx-lifecycle-viewmodelCompose` | 2.9.1  |
| Navigation Compose| `navigation-compose`       | 2.8.0-alpha13 |
| Koin DI          | `koin-core`, `koin-android`, `koin-compose` | 4.1.0 |
| SQLDelight       | `sqldelight-android`, `sqldelight-darwin` | 2.1.0 |
| Ktor (HTTP)      | `ktor-client-*`            | 3.2.3       |
| kotlinx-datetime | `kotlin-datetime`          | 0.6.2       |

See the `[libraries]` section in your version catalog for full aliases and details.

---

## File Structure

- `ui/NoteListScreen.kt`:   List and manage notes, click to view/edit.
- `ui/PdfViewerScreen.kt`:  Platform-specific PDF viewer (Compose UI, expects local file).
- `ui/NoteAddEditScreen.kt`: Add or edit a note, with animated feedback.
- `ui/DatePickerLocalDate.kt`: Date picker for note creation date.
- `model/Note.kt`:          Note data model.
- `NoteViewModel.kt`:       Shared multiplatform ViewModel with state flows.
- `NoteRepository.kt`:      Interface for note persistence (SQLDelight recommended).

---

## Usage Example


@Composable
fun MainApp(viewModel: NoteViewModel) {
    NotesListScreen(
        viewModel = viewModel,
        onNoteClick = { noteId -> /* Navigate to note details / },
        onAddNote = { / Navigate to add note screen */ }
    )
}

## How To Build

- Configure the project using the provided plugins and libraries in the `libs.versions.toml` or build scripts.
- Inject `NoteViewModel` using Koin.
- Use `NotesListScreen`, `NoteAddEditScreen`, and `PdfViewerScreen` in your navigation flow.

---

## PDF Viewer (Compose KMP)

- Use `PdfViewerScreen` with a supplied file path (platform-specific actual, e.g., Android PdfRenderer, iOS PDFKit via Compose interop).

---

## Quick Reference

**Library Aliases Used:**

- `koin-core`, `koin-android`, `koin-compose`, `koin-viewmodel`
- `composeMultiplatform`, `androidx-activity-compose`, `androidx-lifecycle-viewmodelCompose`, `androidx-lifecycle-runtimeCompose`
- `sqldelight-runtime`, `sqldelight-android`, `sqldelight-darwin`, `sqldelight-coroutine`
- `ktor-core`, `ktor-client-okhttp`, `ktor-client-ios`, `ktor-serialization-kotlinx-json`
- `kotlin-datetime`
- And all Compose/AndroidX essentials

---

**Let me know if you need usage samples or wiring for DI, navigation, or platform-specific features!**
