package com.codewiththiru.remoteconfig.experiment

import java.security.MessageDigest

interface UserBucketingStrategy {
    fun assignUserToVariant(userId: String, experimentId: String, variants: List<ExperimentVariant>): ExperimentVariant?
}

class DeterministicHashingStrategy : UserBucketingStrategy {
    override fun assignUserToVariant(userId: String, experimentId: String, variants: List<ExperimentVariant>): ExperimentVariant? {
        if (variants.isEmpty()) return null
        val totalWeight = variants.sumOf { it.weight }
        if (totalWeight <= 0) return null

        val hashInput = "$userId-$experimentId"
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(hashInput.toByteArray())
        
        val hashInt = ((hashBytes[0].toInt() and 0xFF) shl 24) or
                ((hashBytes[1].toInt() and 0xFF) shl 16) or
                ((hashBytes[2].toInt() and 0xFF) shl 8) or
                (hashBytes[3].toInt() and 0xFF)
        
        val hashValue = (hashInt.toUInt().toLong() % 100).toInt()
        
        var currentWeight = 0
        for (variant in variants.sortedBy { it.id }) {
            currentWeight += variant.weight
            if (hashValue < currentWeight) {
                return variant
            }
        }
        return null
    }
}
