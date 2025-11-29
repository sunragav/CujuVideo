package com.cuju.videoSdk.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoMetaData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val videoUri: String,
    val thumbNailUri: String,
    val title: String,
    val timeStamp: String,
    val lifeCycleState: String
)
