package com.scdmonitor.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * RiskScore stores the computed probability and a human-readable level.
 */
@Entity(tableName = "risk_scores")
data class RiskScore(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val probability: Double, // 0.0 - 1.0
    val level: String // GREEN / AMBER / RED
)
