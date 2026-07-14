package com.codewiththiru.billing.fraud

import android.util.Base64
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.X509EncodedKeySpec

class ReceiptVerifier(private val base64PublicKey: String?) {
    
    fun verifyReceipt(purchaseJson: String, signature: String): Boolean {
        if (base64PublicKey.isNullOrBlank()) {
            android.util.Log.w("CWT_PLATFORM", "Receipt verification skipped because base64PublicKey is null or empty.")
            return true // Fallback or strict mode could be added here
        }

        if (purchaseJson.isEmpty() || signature.isEmpty()) {
            return false
        }

        return try {
            val keyFactory = KeyFactory.getInstance("RSA")
            val decodedKey = Base64.decode(base64PublicKey, Base64.DEFAULT)
            val publicKeySpec = X509EncodedKeySpec(decodedKey)
            val publicKey = keyFactory.generatePublic(publicKeySpec)

            val sig = Signature.getInstance("SHA1withRSA")
            sig.initVerify(publicKey)
            sig.update(purchaseJson.toByteArray())
            
            val signatureBytes = Base64.decode(signature, Base64.DEFAULT)
            sig.verify(signatureBytes)
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            android.util.Log.e("CWT_PLATFORM", "Error during receipt verification", e)
            false
        }
    }
}
