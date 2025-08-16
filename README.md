# Personal Notes Compose KMP

This Kotlin Multiplatform project implements a personal notes application UI using Jetpack Compose with shared ViewModel and business logic. It features note creation, editing, and listing using modern Compose UI and Kotlin Flow for reactive updates.

---

## Features

- **Add/Edit Notes** with title, HTML body, and created date using a date picker.
- **List Notes** with clickable items displaying title, summary, and creation date.
- Animated user feedback on successful save or errors.
- Uses Kotlin Multiplatform for shared logic and ViewModel.
- Integration-ready for Koin dependency injection.

---

## Project Structure

- **Model:** Represents the Note data structure.
- **ViewModel:** Manages notes list and add/edit operations via Kotlin Flow.
- **UI Composables:**  
  - `NoteAddEditScreen` — Add or edit a single note with form inputs.  
  - `NotesListScreen` — Displays all notes in a scrollable list.  
  - `DatePickerLocalDate` — A reusable date picker component.

---

## Getting Started

### Prerequisites

- Kotlin 1.9+ and Kotlin Multiplatform setup
- Jetpack Compose with Material3 support
- Koin for Dependency Injection
- Android Studio Arctic Fox or later

### Usage

1. Inject and initialize the `NoteViewModel`.
2. Use `NotesListScreen` as your main screen to display notes.
3. Navigate to `NoteAddEditScreen` to add or edit notes.
4. Use animated success and error messages to improve UX.
5. Integrate with platform-specific storage and persistence.

---
---
