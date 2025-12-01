package com.cuju.videoSdk.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoMetaData(
    @PrimaryKey(autoGenerate = false)
    val videoUri: String,
    val thumbNailUri: String,
    val timeStamp: String,
    val fileName: String,
    val lifeCycleState: String
)
