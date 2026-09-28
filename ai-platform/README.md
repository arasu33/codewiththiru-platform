# 🧠 Enterprise AI Platform & Intelligence Engine (`:ai-platform`)

Enterprise AI platform providing multi-provider LLM integrations (Google Gemini, OpenAI, Anthropic Claude, local Ollama), persistent conversation memory, RAG, and intelligent recommendations.

---

## 🚀 Features

- **Multi-Provider Architecture**: Swap between Gemini, OpenAI, Claude, or local on-device models with a single interface.
- **AI Chat & Assistants**: Streaming token responses, chat message histories, and role-based personas.
- **RAG & Vector Knowledge**: Context retrieval for documents, FAQs, and application data.
- **Content Generation**: Automated question generation, hints, summaries, and educational content.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:ai-platform")
}
```

---

## ⚠️ Consumer Prerequisites

### 1. Permissions (`AndroidManifest.xml`)
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 2. API Keys
Configure your LLM API Key securely (e.g. via `local.properties` or environment variable):
```kotlin
val aiManager = AiManager(
    provider = GeminiProvider(apiKey = BuildConfig.GEMINI_API_KEY)
)
```
