# WeatherApp

# Overview
WeatherApp is a weather application that allows users to get current weather details and forecast information based on their location or a searched city. This project demonstrates the implementation of Clean Architecture, Jetpack Compose, modular architecture, and modern Android development practices.

# Features:
- **Current Weather:** Displays current weather conditions for the user's location or any searched city.
- **Forecast Weather:** Provides detailed weather forecasts for upcoming days.
- **Location Services:** Automatically retrieves the user's location to display relevant weather details.
- **Search Feature:** Allows users to search for weather in a specific city.
- **Toggle theme:** Allows users to toggle theme of the application.

# Technology Stack:
- **Languages & Frameworks**
  - Kotlin: For Android development.
  - Jetpack Compose: For building modern and declarative UIs.
- **Libraries & Tools**
  - Hilt: For dependency injection, ensuring better code modularity and readability.
  - Retrofit: For making network requests and parsing JSON responses.
  - Room: For local data persistence.
  - SharedPreferences: For storing simple data like user preferences.
  - [WeatherUtilsLibrary](https://github.com/MohamedGharieb19/WeatherUtilsLibrary.git): A custom library authored by me, providing utilities for date formatting and weather-related functions.
- **Architecture**
  - Clean Architecture: Encourages separation of concerns.
  - Modular Design:
    - **Core Module:** Contains shared functionalities, constants, and reusable logic such as location services.
    - **Features Module:** Separate modules for Weather and Forecast, each with its own Data, Domain, and Presentation layers.

# Unit Tests:
- Unit tests cover critical components like ViewModels, Use Cases, and Mappers.
- **Local Testing:** All unit tests pass successfully in the local environment.
- **CI/CD Environment:** One test fails due to runtime differences in the CI environment. The pipeline sets the `TZ=UTC` environment variable to standardize time-sensitive logic, but discrepancies remain under investigation.

# CI/CD Pipeline:
- **Linting:** Ensures code adheres to standard formatting and best practices.
- **Testing:** Executes unit tests to verify application stability.
- **Building:** Automates APK generation for deployment.
