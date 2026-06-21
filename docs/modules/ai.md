# AI Module

## Purpose
The `ai` module powers intelligent features across the CodeWithThiru platform. It blends on-device machine learning via Google ML Kit (for low-latency tasks like OCR or face detection) with cloud-based Generative AI via the Gemini API (for advanced LLM tasks like code explanation and conversational agents).

## Architecture
- **AiServiceManager**: A facade that routes requests to either local ML Kit processors or cloud Gemini endpoints based on the task and network availability.
- **Local Model Manager**: Handles downloading and lifecycle management of ML Kit models.
- **Generative Client**: Wraps the Google AI Client SDK, managing API keys and prompt templates securely.

## Public APIs
- `AiManager`: Main entry point for interacting with AI features.
- `VisionAnalyzer`: For processing images (OCR, object detection).
- `ChatAssistant`: Interfaces with the Gemini LLM for conversational AI.
- `generateExplanation(code: String): Flow<String>`: Streams an explanation from the LLM.

## Configuration
Cloud-based models require API keys (e.g., Google AI Studio keys). These must be fetched securely from the backend or Remote Config.

## Dependencies
```gradle
// ML Kit for on-device ML
implementation("com.google.mlkit:text-recognition:16.0.0")
implementation("com.google.mlkit:face-detection:16.1.5")

// Gemini for Generative AI
implementation("com.google.ai.client.generativeai:generativeai:0.2.2")
```

## Initialization
Initialize ML Kit analyzers lazily to save memory. The Gemini client should be configured with the appropriate API key and model identifier (e.g., `gemini-1.5-flash`).
```kotlin
val generativeModel = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = BuildConfig.GEMINI_API_KEY
)
```

## Integration Steps
1. Add necessary ML Kit dependencies based on required features.
2. Store the Gemini API key in `local.properties` (never commit this) and expose it via `BuildConfig`.
3. Implement `ImageAnalysis.Analyzer` if using CameraX with ML Kit.

## Required Permissions
- `android.permission.INTERNET` (For Gemini and downloading ML Kit models)
- `android.permission.CAMERA` (If integrating with live camera feeds)

## Manifest Entries
To auto-download ML Kit models upon app install, add:
```xml
<meta-data
    android:name="com.google.mlkit.vision.DEPENDENCIES"
    android:value="ocr,face" />
```

## Remote Config & Analytics Dependencies
- **Remote Config**: Store prompt templates to update AI behavior without app releases. Toggle AI features on/off to manage API costs.
- **Analytics**: Track `ai_request_latency`, `ai_error`, and `ai_feature_used`.

## Security
- **API Keys**: Do not hardcode API keys in the source code. Use a secure backend proxy for production to prevent key extraction and rate-limit abuse.
- **Data Privacy**: Ensure user PII is stripped before sending prompts to cloud LLMs.

## Accessibility
Ensure streamed text from LLMs handles screen reader focus correctly (e.g., announce when generation is complete, rather than speaking every token).

## Testing
- Use dependency injection to provide mock `GenerativeModel` instances that return static responses for UI testing.
- Test ML Kit locally using static sample images.

## Migration
When upgrading Gemini models (e.g., from `gemini-pro` to `gemini-1.5-flash`), update the `modelName` parameter and ensure the prompt structure remains compatible.

## Troubleshooting
- **Model Download Failures**: ML Kit models may fail to download on metered connections. Implement retry logic and fallback UI.
- **Quota Exceeded (429)**: Gemini APIs have rate limits. Implement exponential backoff and graceful degradation in the UI.

## Examples
```kotlin
suspend fun askGemini(prompt: String) {
    try {
        val response = generativeModel.generateContent(prompt)
        println(response.text)
    } catch (e: Exception) {
        Logger.e(e, "AI Generation failed")
    }
}
```
