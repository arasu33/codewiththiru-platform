# AI Platform Architecture

The `AIPlatformManager` handles routing requests to LLMs (OpenAI, Gemini, Local LLM).
Agents run `AgentWorkflow` instances utilizing `MemoryManager` to build conversational context.
