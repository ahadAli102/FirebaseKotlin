package com.ahad.firebasekotlin.storage

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns

sealed class FileUtil{
    companion object{
        fun getFileName(uri: Uri,contentResolver:ContentResolver): String? {
            var result: String? = null
            if (uri.scheme == "content") {
                val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
                cursor.use {
                    if (it != null && it.moveToFirst()) {
                        result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
            if (result == null) {
                result = uri.path
                val cut = result!!.lastIndexOf('/')
                if (cut != -1) {
                    result = result!!.substring(cut + 1)
                }
            }
            return result
        }
    }
}
