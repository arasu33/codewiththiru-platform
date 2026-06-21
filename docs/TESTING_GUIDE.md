# Testing Guide

This document outlines the testing strategy, tools, and best practices for the CodeWithThiru Platform. We enforce a robust testing culture to ensure stability across our multi-module architecture.

## Testing Pyramid

1.  **Unit Tests (70%):** Fast, isolated tests for ViewModels, UseCases, and Repositories.
2.  **Integration Tests (20%):** Testing interactions between modules (e.g., Database + Repository).
3.  **UI/E2E Tests (10%):** User journey tests using Compose Test Rule and UI Automator.

---

## 1. Unit Testing

We use **JUnit 5 (Jupiter)** and **MockK** for local unit testing.

### ViewModels and Coroutines

Use the `MainDispatcherRule` (from our test-utils module) to swap out the `Dispatchers.Main` dispatcher for `TestCoroutineDispatcher`.

```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk()
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel(authRepository)
    }

    @Test
    fun `login success updates state`() = runTest {
        coEvery { authRepository.login(any(), any()) } returns Result.success(User())
        
        viewModel.login("test@test.com", "password")
        
        assertEquals(AuthState.Success, viewModel.uiState.value)
    }
}
```

---

## 2. Integration Testing

Integration tests run on the JVM using Robolectric or on an Android Emulator.

### Room Database

Test DAOs using an in-memory database to ensure fast execution and no state leakage.

```kotlin
@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = database.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }
}
```

---

## 3. UI Testing (Jetpack Compose)

Use the `composeTestRule` to interact with UI components in isolation or within a fragment/activity.

```kotlin
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginButton_isDisabled_whenInputIsInvalid() {
        composeTestRule.setContent {
            CodeWithThiruTheme {
                LoginScreen(state = LoginState(email = "", password = ""), onAction = {})
            }
        }

        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()
    }
}
```

---

## 4. Screenshot Testing (Roborazzi)

We use **Roborazzi** for fast JVM-based screenshot testing of our Compose UI components to catch visual regressions.

1.  Write a standard Compose test.
2.  Use the `captureRoboImage()` extension.

```kotlin
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ButtonComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPrimaryButton() {
        composeTestRule.setContent {
            PrimaryButton(text = "Submit", onClick = {})
        }
        
        // Will generate or verify against an existing reference image
        composeTestRule.onRoot().captureRoboImage("src/test/screenshots/primary_button.png")
    }
}
```

Run the Gradle task to record baseline images:
`./gradlew recordRoborazziDebug`

Run the Gradle task to verify images in CI:
`./gradlew verifyRoborazziDebug`
