package com.cuju.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.cuju.core.applicationContext

inline fun <reified T : RoomDatabase> getDatabaseBuilder(name: String): RoomDatabase.Builder<T> {
    val dbFile = applicationContext.getDatabasePath(name)
    return Room.databaseBuilder<T>(
        context = applicationContext,
        name = dbFile.absolutePath
    )
}

inline fun <reified T : RoomDatabase> getRoomDatabase(
    name: String,
    vararg migrations: Migration
): T {
    return getDatabaseBuilder<T>(name)
        .addMigrations(*migrations)
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .build()
}
