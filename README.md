# MyFood App

## Overview
MyFood is a feature-rich food application built using **Jetpack Compose** and **MVVM architecture**, leveraging a **clean architecture approach**. It enables users to explore, add, update, and manage food items with categories, tags, and images.

## Features
- Fetch all food items with images, categories, and tags
- Fetch a single food item with detailed information
- Create new food items
- Update existing food items  ( not completed)
- Fetch all categories and tags
- Clean and structured navigation
- Dependency Injection using **Hilt**

---

## Project Structure

The project follows a **multi-layered architecture** with clear separation of concerns:

```
app/src/main/java/com/rhymezxcode/food
├── data
│   ├── api
│   ├── model
│   ├── repository
├── di
├── navigation
├── theme
├── ui
│   ├── component
│   ├── viewModel
├── util
```

### 1. **Data Layer**
The `data` package is responsible for handling data operations such as API calls and local data storage.

#### 📌 `api/`
- **FoodApiService.kt** → Defines Retrofit API endpoints for interacting with the backend.

#### 📌 `model/`
Contains data models for API responses and requests, structured into different categories:
- **categories/** → Models for food categories
- **createFood/** → Request model for creating food items
- **fetchAllFoodModel/** → Models for fetching all food items
- **fetchOneFoodModel/** → Models for fetching a single food item
- **tags/** → Models for food tags
- **updateFood/** → Request model for updating food items

#### 📌 `repository/`
Repositories handle data sources and abstract the API logic from ViewModels. Each repository has an **interface** and **implementation**.
- Example:
  - `FetchAllFoodRepository.kt` (Interface)
  - `FetchAllFoodRepositoryImpl.kt` (Implementation)

---

### 2. **Dependency Injection (DI)**
The `di` package contains:
- **AppModule.kt** → Provides dependency injection using **Hilt** to supply repositories, API services, and other dependencies.

---

### 3. **Navigation**
The `navigation` package manages the **screen-to-screen flow** of the app.
- **AppNavigation.kt** → Defines composable navigation destinations.
- **NavigationItem.kt** → Holds different navigation routes.

---

### 4. **UI Layer**
The `ui` package contains all **screens**, **UI components**, and **ViewModels**.

#### 📌 `component/`
Reusable UI components such as:
- **FoodItemCard.kt** → Displays food items in a structured way
- **CategoryDropDown.kt** → Dropdown for selecting categories
- **CustomTagChip.kt** → Custom tag UI for food items
- **ImageButton.kt** → Button for image selection

#### 📌 Screens
- **AddFoodScreen.kt** → UI for adding food items
- **HomeScreen.kt** → Displays all food items
- **ShowFoodScreen.kt** → Displays a single food item’s details
- **MyBottomNavigationBar.kt** → Handles bottom navigation

#### 📌 `viewModel/`
Manages UI logic and interacts with repositories.
- Example:
  - `FetchAllFoodViewModel.kt` → Fetches all food items
  - `CreateFoodViewModel.kt` → Handles adding new food items
  - `UpdateFoodViewModel.kt` → Manages updating food items

---

### 5. **Utils**
The `util` package contains:
- **Constants.kt** → Holds constant values (e.g., API endpoints)
- **Extensions.kt** → Provides useful extension functions
- **Route.kt** → Defines navigation routes

---

## Tech Stack
- **Kotlin** → Main programming language
- **Jetpack Compose** → UI Toolkit for building modern UIs
- **Retrofit** → API calls
- **Hilt (Dagger-Hilt)** → Dependency Injection
- **MVVM (Model-View-ViewModel)** → Architecture pattern
- **Coroutines & Flow** → Asynchronous programming

---

## Dependencies
The project includes the following dependencies for static analysis, version control, and performance improvements:

- **[Ktlint](/documentation/StaticAnalysis.md)** → Code formatting.
- **[Detekt](/documentation/StaticAnalysis.md)** → Code smell detection.
- **[Git Hooks](/documentation/GitHooks.md)** → Automatically performs static analysis checks.
- **[Gradle Versions Plugin](/documentation/VersionsPlugin.md)** → Checks for dependency updates.
- **[GitHub Actions](/documentation/GitHubActions.md)** → Continuous integration and code quality checks.
- **[LeakCanary](https://square.github.io/leakcanary/)** → Memory leak detection.
- **[Hilt](https://developer.android.com/training/dependency-injection/hilt-android)** → Dependency Injection.
- **[Room](https://developer.android.com/training/data-storage/room)** → Local database.
- **[Paparazzi](https://github.com/cashapp/paparazzi)** → UI testing.

---

## Setup & Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/rhymezxcode/MyFood.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle dependencies.
4. Run the app on an emulator or a physical device.

---

## Contribution
Feel free to contribute! Create a pull request with your changes, and I’ll be happy to review it.

---

## License
This project is licensed under the **MIT License**.

---

## Contact
For inquiries, reach out at [rhymezx.code@gmail.com](mailto:rhymezx.code@gmail.com).

