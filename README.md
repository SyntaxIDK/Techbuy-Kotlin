# TechBuy: Android E-commerce Application

TechBuy is a mobile application for Android that allows users to browse and purchase tech products.

## Key Features

- User registration and login
- Browse a wide range of tech products
- Search for specific products
- View detailed product information and specifications
- Add products to a shopping cart
- Save favorite items to a wishlist
- Seamless checkout process
- Order confirmation and history
- Manage user profile and preferences

## Project Structure

The project follows a standard Android application structure:

- `app/src/main/java/`: Contains the Kotlin source code for the application, organized by features (e.g., `com.example.techbuy.ui.screens`, `com.example.techbuy.data.models`).
- `app/src/main/res/`: Includes all application resources, such as layouts (XML), drawables (images, icons), navigation graphs, and values (strings, colors, themes).
- `gradle/`: Contains Gradle-specific files, including the version catalog (`libs.versions.toml`).
- `build.gradle.kts` (project level) and `app/build.gradle.kts` (app module level): These files define the build configurations, dependencies, and plugins for the project and the app module, respectively.

## Getting Started

To build and run the TechBuy application:

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/Techbuy-latest.git
    ```

2.  **Open in Android Studio:**

    - Launch Android Studio.
    - Select "Open an Existing Project".
    - Navigate to the cloned/downloaded project directory and select it.

3.  **Sync Gradle:**

    - Android Studio should automatically start syncing the project with Gradle. If not, click on "Sync Project with Gradle Files" (often represented by an elephant icon with a refresh symbol) in the toolbar.

4.  **Run the Application:**
    - Once Gradle sync is complete, select an Android Emulator or connect a physical Android device.
    - Click the "Run 'app'" button (green play icon) in the toolbar.

The application should now build and install on the selected emulator/device.

## Contributing

Contributions are welcome! If you'd like to contribute to TechBuy, please fork the repository and submit a pull request with your changes.
