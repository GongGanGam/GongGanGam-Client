package com.example.GongGanGam.util

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.Nullable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


object FormDataUtil {

    @Nullable
    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val contentResolver: ContentResolver = context.contentResolver ?: return null

        // create file path
        val filePath: String = (context.applicationInfo.dataDir + File.separator
                + System.currentTimeMillis())

        val file = File(filePath)

        try {
            // use contentResolver
            val inputStream = contentResolver.openInputStream(uri) ?: return null

            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: IOException) {
            return null
        }
        return file.absolutePath
    }
}