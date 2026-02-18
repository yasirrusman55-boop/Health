package com.scdmonitor.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * CrisisEvent represents a logged or detected vaso-occlusive crisis event.
 */
@Entity(tableName = "crisis_events")
data class CrisisEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val notedBy: String?, // "auto" or clinician/user id
    val confirmed: Boolean = false,
    val details: String?
)
