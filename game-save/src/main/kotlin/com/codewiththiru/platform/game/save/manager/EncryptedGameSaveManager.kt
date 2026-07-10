package com.codewiththiru.platform.game.save.manager

import com.codewiththiru.platform.game.save.api.SaveRequest
import com.codewiththiru.platform.game.save.api.SaveResponse
import com.codewiththiru.platform.game.save.api.SaveSlot
import com.codewiththiru.platform.game.save.encryption.EncryptionStrategy
import java.io.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EncryptedGameSaveManager<T>(
    private val saveDirectory: File,
    private val encryptionStrategy: EncryptionStrategy,
    private val signatureValidator: SignatureValidator,
    private val serializer: (T) -> ByteArray,
    private val deserializer: (ByteArray) -> T,
) : GameSaveManager<T> {
    private val _status = MutableStateFlow(SaveSystemStatus.IDLE)
    override val status: StateFlow<SaveSystemStatus> = _status.asStateFlow()

    @Suppress("TooGenericExceptionCaught")
    override suspend fun save(request: SaveRequest<T>): SaveResponse<T> {
        _status.value = SaveSystemStatus.SAVING
        return try {
            val rawBytes = serializer(request.state)

            // Encrypt
            val encryptedBytes = encryptionStrategy.encrypt(rawBytes)

            // Sign the ENCRYPTED payload (Encrypt-then-MAC)
            val signature = signatureValidator.generateSignature(encryptedBytes)

            val saveFile = File(saveDirectory, request.slot.id)
            val signatureFile = File(saveDirectory, "${request.slot.id}.sig")

            saveFile.writeBytes(encryptedBytes)
            signatureFile.writeText(signature)

            _status.value = SaveSystemStatus.IDLE
            SaveResponse.Success(request.slot, request.metadata, request.state)
        } catch (e: Exception) {
            _status.value = SaveSystemStatus.ERROR
            SaveResponse.Failure(e, request.slot)
        }
    }

    @Suppress("TooGenericExceptionCaught", "ReturnCount")
    override suspend fun load(slot: SaveSlot): SaveResponse<T> {
        _status.value = SaveSystemStatus.LOADING
        return try {
            val saveFile = File(saveDirectory, slot.id)
            val signatureFile = File(saveDirectory, "${slot.id}.sig")

            if (!saveFile.exists() || !signatureFile.exists()) {
                _status.value = SaveSystemStatus.ERROR
                return SaveResponse.Failure(Exception("Save file or signature not found"), slot)
            }

            val encryptedBytes = saveFile.readBytes()
            val expectedSignature = signatureFile.readText()

            // Verify signature
            if (!signatureValidator.validateSignature(encryptedBytes, expectedSignature)) {
                _status.value = SaveSystemStatus.ERROR
                return SaveResponse.Failure(Exception("Save file signature mismatch! Potential tamper detected."), slot)
            }

            // Decrypt
            val rawBytes = encryptionStrategy.decrypt(encryptedBytes)
            val data = deserializer(rawBytes)

            // Dummy metadata for load (in real app, metadata should be stored alongside the save)
            val dummyMetadata =
                com.codewiththiru.platform.game.save.api.SaveMetadata(
                    timestampMs = saveFile.lastModified(),
                    schemaVersion = 1,
                    playtimeSeconds = 0,
                    gameId = "dummy",
                    gameVersion = "1.0",
                )

            _status.value = SaveSystemStatus.IDLE
            SaveResponse.Success(slot, dummyMetadata, data)
        } catch (e: Exception) {
            _status.value = SaveSystemStatus.ERROR
            SaveResponse.Failure(e, slot)
        }
    }

    override suspend fun delete(slot: SaveSlot): Boolean {
        val saveFile = File(saveDirectory, slot.id)
        val signatureFile = File(saveDirectory, "${slot.id}.sig")
        return saveFile.delete() && signatureFile.delete()
    }

    override suspend fun listSaves(): List<SaveResponse<T>> {
        // Implementation for listing saves (basic return empty for now since we only need saving/loading for MVP)
        return emptyList()
    }

    override suspend fun sync() {
        _status.value = SaveSystemStatus.SYNCING
        // Delegate to GameSync module
        _status.value = SaveSystemStatus.IDLE
    }
}
