# Task Manager Android Application

[![Android CI](https://github.com/YOUR_USERNAME/YOUR_REPOSITORY/actions/workflows/android.yml/badge.svg)](https://github.com/YOUR_USERNAME/YOUR_REPOSITORY/actions/workflows/android.yml)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

**Task Manager** is a modern Android application built with Jetpack Compose that helps you manage your tasks efficiently. It allows you to create, organize, prioritize, and track your tasks with a clean and intuitive user interface.

## Features

-   **Task Creation:**
    -   Create new tasks with titles and descriptions.
    -   Set due dates for tasks.
    -   Assign priorities (High, Medium, Low) to tasks.
-   **Task Management:**
    -   View a list of all tasks.
    -   Mark tasks as completed.
    -   Delete tasks.
    -   Swipe to delete or complete tasks.
-   **Task Details:**
    -   View detailed information about a task, including its title, description, due date, and priority.
-   **Filtering:**
    -   Filter tasks by status (All, Pending, Completed).
-   **User Interface:**
    -   Clean and modern UI built with Jetpack Compose.
    -   Curved bottom sheet for filtering options.
    -   Sentence case text formatting.
    -   Automatic focus on the task title field when creating a new task.
    -   "Done" IME action to dismiss the keyboard.
- **Empty State:**
    - Display a placeholder when there are no tasks.
- **Overdue Tasks:**
    - Highlight overdue tasks.
- **Navigation:**
    - Navigate between the task list and task detail screens.
- **Error Handling:**
    - Display error messages when task creation or validation fails.
- **Accessibility:**
    - Includes content descriptions for icons.

## Tech Stack

-   **Kotlin:** The primary programming language.
-   **Jetpack Compose:** Modern UI toolkit for building native Android UIs.
-   **Material 3:** Material Design components for Compose.
-   **Navigation Compose:** For in-app navigation.
-   **Room:** For local data persistence.
-   **Kotlin Coroutines:** For asynchronous programming.
-   **Dependency Injection:** Hilt for dependency injection.
- **Lottie:** For displaying animations.
- **Kotlinx DateTime:** For handling dates and times.

## Architecture

The application follows a clean architecture, separating concerns into distinct layers:

-   **UI Layer:** Jetpack Compose composables for the user interface.
-   **Domain Layer:** Use cases for business logic.
-   **Data Layer:** Repositories and data sources for data access.
- **Room:** Local database.

## Getting Started

1.  **Prerequisites:**
    -   Android Studio (latest version recommended).
    -   Android SDK.
    -   Git.
2.  **Clone the repository:**