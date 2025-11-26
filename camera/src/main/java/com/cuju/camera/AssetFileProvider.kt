package com.cuju.camera

import android.content.ContentProvider
import android.content.ContentValues
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap

class AssetFileProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun getType(uri: Uri): String {
        val segments = uri.pathSegments
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val mimeType = when (segments[0]) {
            "icon" -> MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg")
            else -> MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        } ?: "application/octet-stream"
        return mimeType
    }

    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        val segments = uri.pathSegments
        return when (segments[0]) {

            "photo", "video" -> {
                val filename = segments[1]
                context?.resources?.assets?.openFd(filename)
            }

            else -> null
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        val segments = uri.pathSegments
        return when (segments[0]) {
            "icon", "video", "photo" -> {
                val columns = arrayOf(
                    OpenableColumns.DISPLAY_NAME,
                )

                return MatrixCursor(columns).apply {
                    addRow(arrayOf(segments.last()))
                }
            }

            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("No insert")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        throw UnsupportedOperationException("No update")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("No delete")
    }
}
