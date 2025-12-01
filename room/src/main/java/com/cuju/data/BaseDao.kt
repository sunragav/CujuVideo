package com.cuju.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Upsert
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.cuju.core.getSuperclassTypeArgumentAsSimpleName

@Dao
@Suppress("FunctionNaming", "TooManyFunctions")
abstract class BaseDao<T> {

    // table name is the same as the DAO entity class name
    private val tableName get() = this::class.getSuperclassTypeArgumentAsSimpleName(BaseDao::class)
    @Upsert
    abstract suspend fun upsert(obj: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(obj: List<T>)

    @Upsert
    abstract suspend fun upsert(obj: List<T>)

    @Delete
    abstract suspend fun delete(obj: T)

    @Delete
    abstract suspend fun delete(obj: List<T>)

    open suspend fun all() = execute(SimpleSQLiteQuery("SELECT * FROM `$tableName`"))

    suspend fun subsetByColumn(column: String, value: String) =
        execute(SimpleSQLiteQuery("SELECT * FROM `$tableName` WHERE $column = ?", arrayOf(value)))

    @Transaction
    open suspend fun replaceAll(newObjects: List<T>, comparator: (T, T) -> Boolean) {
        replace(all(), newObjects, comparator)
    }

    @Transaction
    open suspend fun replaceSubset(
        column: String,
        columnValue: String,
        newObjects: List<T>,
        comparator: (T, T) -> Boolean
    ) {
        replace(subsetByColumn(column, columnValue), newObjects, comparator)
    }

    private suspend fun replace(
        storedObjects: List<T>,
        newObjects: List<T>,
        comparator: (T, T) -> Boolean
    ) {
        upsert(newObjects)
        val objectsToDelete =
            storedObjects.filter { stored -> newObjects.none { comparator(it, stored) } }
        delete(objectsToDelete)
    }

    @RawQuery
    protected abstract suspend fun execute(query: SupportSQLiteQuery): List<T>
}
