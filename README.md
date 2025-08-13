# Car Gallery App

A simple Android app that displays car images fetched from the Mobile.de API.

## Features

- Fetches car images directly from the Mobile.de API.
- Displays images in a gallery using **Jetpack Compose**.
- Shows full-size car images in a **dialog** when a thumbnail is clicked.
- Basic loading and error handling.

## Notes

- UI is minimal and straightforward.
- Simplified implementation for speed:
    - No domain layer or DTO mapping.
    - Detail screen replaced with a dialog instead of a separate page.

## Architecture

- **MVVM** (ViewModel + UIState)
- **StateFlow** for reactive UI updates
- Separation between **data fetching** (repository) and **presentation** (Compose UI)

### Layers

- **Data Layer:** Repository fetching API data
- **Presentation Layer:** Compose UI + ViewModel with UIState (`Loading`, `Success`, `Error`)

## Tech Stack & Libraries

- Kotlin
- Jetpack Compose
- Retrofit (networking)
- Coil (image loading)
- Koin (dependency injection)
- Coroutines (async operations)
- Kotlin Flow (state management)
- MockK (unit testing)
