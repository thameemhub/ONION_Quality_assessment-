# OnTest — AI-Powered Onion Quality Assessment 🧅

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)

**OnTest** is an innovative Android prototype application that simulates an AI-powered onion quality grading ecosystem. Designed for farmers and agricultural buyers, it streamlines the assessment of onion batches using on-device camera analysis, instantly identifying rot risks, determining Unmarketable Rot Size (URS), and generating actionable quality reports.

---

## 📖 Case Study

**The Problem:** Traditional onion grading is a manual, labor-intensive, and subjective process. Farmers often lack objective metrics to price their yields, while buyers risk purchasing batches with hidden rot.

**The Solution (OnTest):** 
OnTest digitizes the grading workflow. By taking a quick picture of an onion basket, the app simulates an AI vision model that calculates the rot percentage, assigns a quality grade (Grade A, B, or C), and immediately integrates the data into a local marketplace ecosystem. This empowers farmers to track their historical yield quality and transparently list their goods for buyers.

---

## 🔄 End-to-End Workflow

1. **Onboarding:** A sleek introductory flow explaining the app's core value proposition.
2. **Dashboard (Home):** A central hub displaying quick metrics, recent scans, and quick actions.
3. **Calibration & Scanning:** A guided camera interface (`CameraX`) ensuring the user centers the onion basket perfectly before capturing.
4. **Analysis Simulation:** A dynamic loading state simulating a machine learning inference pipeline analyzing rot boundaries and quality.
5. **Grading Report:** A detailed breakdown of the batch (e.g., "12% Rot Risk - Grade B"). Includes prompt to handle unmarketable onions.
6. **Marketplace Integration:** Users can immediately convert their graded batch into a marketplace listing.
7. **History & Reputation:** Persistent local storage allows users to view all past scans and track their overall quality reputation score over time.

---

## 🏗️ System Architecture

OnTest is built using modern Android development best practices:

*   **Architecture Pattern:** Strict **MVVM** (Model-View-ViewModel) architecture for separation of concerns.
*   **UI Layer:** Fully declarative UI using **Jetpack Compose**, integrated with Material 3 design guidelines.
*   **Navigation:** **Jetpack Navigation Compose** handling complex flows, single-top routing, and back-stack management.
*   **Data Layer:** 
    *   **Room Database:** Handles structured, persistent storage for grading reports and marketplace listings.
    *   **DataStore (Preferences):** Manages lightweight asynchronous key-value storage (e.g., onboarding completion states).
*   **Hardware Integration:** **CameraX** API for stable, lifecycle-aware camera previews and image captures.

---

## 💻 Tech Stack

*   **Language:** Kotlin
*   **UI Toolkit:** Jetpack Compose (Material 3)
*   **Local Persistence:** Room (SQLite), Jetpack DataStore
*   **Hardware:** CameraX
*   **Concurrency:** Kotlin Coroutines & StateFlow / SharedFlow
*   **Permissions:** Accompanist Permissions

---

## 🚀 Getting Started

### Prerequisites
*   **Android Studio:** Ladybug (or newer recommended)
*   **Java Development Kit (JDK):** JDK 17 (Required for Gradle build compatibility)
*   **Android SDK:** API Level 36 (VanillaIceCream)

### How to Clone & Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/thameemhub/ONION_Quality_assessment-.git
   cd ONION_Quality_assessment-
   ```

2. **Open in Android Studio:**
   * Launch Android Studio.
   * Select **File > Open** and choose the cloned repository folder.

3. **Configure JDK 17:**
   * Navigate to **File > Settings > Build, Execution, Deployment > Build Tools > Gradle** (or Android Studio > Preferences on Mac).
   * Ensure the **Gradle JDK** is set to version 17 (e.g., Amazon Corretto 17, Eclipse Temurin 17).
   * *Alternatively*, you can specify the path in `gradle.properties`:
     ```properties
     org.gradle.java.home=/path/to/your/jdk17
     ```

4. **Build and Run:**
   * Let Gradle sync completely.
   * Connect an Android device via USB (with USB Debugging enabled) or start an Android Emulator.
   * Click the green **Run 'app'** button in Android Studio (or run `./gradlew assembleDebug`).

---

## 🎨 Design System

The app utilizes a strictly enforced, premium color palette to convey trust and agricultural context:
*   **Primary:** Violet (`#B9A6E0`) - Used for primary actions and branding.
*   **Secondary:** Camel Brown (`#C69C6D`) - Used for marketplace accents and reputation scaling.
*   **Backgrounds:** Crisp whites and deeply contrasting dark modes.
*   **Typography:** Clean sans-serif (Roboto) with elevated font weights for readability.
