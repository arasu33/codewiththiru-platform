# Getting Started

Welcome to the CodeWithThiru Platform! This guide will help you set up your local development environment and run the project for the first time.

## Prerequisites

Before cloning the repository, ensure your system meets the following requirements:

*   **Android Studio:** Jellyfish | 2023.3.1 or newer.
*   **Java Development Kit (JDK):** JDK 17. Ensure your `JAVA_HOME` environment variable is correctly pointed to JDK 17.
*   **Gradle:** The project uses Gradle version catalogs and the Gradle wrapper (version 8.4 or above). You do not need to install Gradle globally.
*   **Git:** Version control system to clone the repository.

## Installation & Clone Steps

1.  **Clone the Repository**
    Open your terminal and run the following command to clone the platform repository to your local workspace:
    ```bash
    git clone https://github.com/arasu33/codewiththiru-platform.git
    cd codewiththiru-platform
    ```

2.  **Open in Android Studio**
    *   Launch Android Studio.
    *   Select **File > Open** and navigate to the cloned `codewiththiru-platform` directory.
    *   Click **OK** to open the project.
    *   Wait for Android Studio to index the files and sync the Gradle project. This may take a few minutes depending on your internet connection (downloading dependencies).

3.  **Configure Environment Variables (If Applicable)**
    If the project requires specific API keys (e.g., Firebase `google-services.json`, AdMob App IDs), ensure they are placed in the respective application module directories or set in your `local.properties` file:
    ```properties
    # local.properties
    # Do not check this file into version control
    sdk.dir=/path/to/your/android/sdk
    API_BASE_URL="https://api.codewiththiru.com/"
    ```

## Verification

To verify that your environment is correctly set up, build and run the application:

1.  **Clean and Rebuild**
    Run the following Gradle tasks from the command line or using the Android Studio Gradle panel:
    ```bash
    ./gradlew clean build
    ```

2.  **Run Unit Tests**
    Ensure all foundational tests are passing:
    ```bash
    ./gradlew testDebugUnitTest
    ```

3.  **Deploy to Emulator/Device**
    *   Create an Android Virtual Device (AVD) running API level 24 or higher, or connect a physical Android device with USB debugging enabled.
    *   Select the `app` configuration from the Run/Debug configurations dropdown.
    *   Click the **Run** button (green play icon) or use the shortcut `Shift + F10`.
    *   The app should compile, install, and launch successfully on the target device.
