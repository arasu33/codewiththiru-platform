# 🧠 AI-Native Platform (`:ai-native-platform`)

Autonomous AI engine powering agents, generative assistants, adaptive personalization, and AI safety rails.

## Features
- **AI Agents**: Extensible agent lifecycle management for autonomous background reasoning.
- **AI Tutors & Assistants**: Conversational engines adaptable to domain-specific knowledge bases.
- **Autonomous Analytics**: Predictive user-engagement modeling and proactive UI adaptation.
- **AI Safety & Policy Guardrails**: Content filtering, prompt injection defense, and output sanitization.

## Quick Start

### 1. Add Dependencies
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:ai-native-platform")
}
```

### 2. Basic Initialization
```kotlin
val agentEngine = AgentEngineBuilder(context)
    .withSafetyGuardrails(StrictSafetyPolicy)
    .build()
```

Refer to `docs/` for specific architecture guides (`AI_AGENTS.md`, `AI_SAFETY.md`, `AI_TUTOR.md`).
