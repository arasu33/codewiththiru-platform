# Identity Module

## Purpose
The `identity` module provides a unified authentication and user profile management system. It abstracts the complexity of traditional logins, OAuth providers (Google, Apple), and the modern Android Credential Manager (Passkeys, One-Tap).

## Architecture
- **AuthRepository**: The central source of truth for the user's session state.
- **CredentialManager Integration**: Handles Passkeys and Google One-Tap.
- **Firebase Auth Wrapper**: Manages backend tokens and third-party credential linking.
- **ProfileManager**: Handles fetching and updating user metadata.

## Public APIs
- `AuthManager`: Exposes methods like `signInWithGoogle()`, `signUpWithEmail()`, `signOut()`.
- `observeAuthState()`: Returns a `Flow<AuthState>` (Authenticated, Unauthenticated, Loading).
- `UserSession`: Data class holding the current user's profile and access tokens.

## Configuration
OAuth Client IDs (Web Client ID for Google Login) must be defined in `strings.xml` or `build.gradle` properties.

## Dependencies
```gradle
implementation("androidx.credentials:credentials:1.2.0")
implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
```

## Initialization
Firebase Auth initializes automatically. The `AuthManager` should be injected as a Singleton to maintain state across the app.

## Integration Steps
1. Register the app's SHA-1/SHA-256 fingerprints in the Firebase Console.
2. Enable desired Auth providers in Firebase.
3. Configure OAuth consent screen in Google Cloud Console.
4. Pass the Web Client ID to the Credential Manager request.

## Required Permissions
- `android.permission.INTERNET`

## Manifest Entries
No special manifest entries are needed, as Credential Manager relies on system UI.

## Remote Config & Analytics Dependencies
- **Analytics**: Logs `login`, `sign_up`, and `logout` events.
- **Remote Config**: Toggles available login methods (e.g., disable email signup during an attack).

## Security
Tokens retrieved from Firebase or Credential Manager are securely stored using the `security` module. Passkeys provide phishing-resistant, cryptographic proof of identity.

## Accessibility
Ensure all login form fields have `contentDescription` and semantic labels. Maintain a logical focus order for hardware keyboards.

## Testing
- Use Firebase Auth Emulator for local testing without affecting production users.
- Use mock `AuthManager` to test authenticated UI states.

## Migration
Migrating from `GoogleSignInClient` to `CredentialManager` requires adopting the new `GetCredentialRequest` APIs to support Passkeys alongside One-Tap.

## Troubleshooting
- **DEVELOPER_ERROR / Code 10**: Usually means the SHA-1 in the console does not match the APK signature, or the Web Client ID is incorrect.
- **Play Services Not Available**: Ensure fallbacks exist (e.g., basic email/password) if Google Play Services are missing.

## Examples
```kotlin
suspend fun signInWithGoogle(context: Context) {
    val request = GetCredentialRequest(
        listOf(
            GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(getString(R.string.web_client_id))
                .build()
        )
    )
    val result = credentialManager.getCredential(context, request)
    // Handle result and link with Firebase
}
```
