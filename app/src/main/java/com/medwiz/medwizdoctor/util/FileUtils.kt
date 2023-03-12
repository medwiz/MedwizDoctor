package com.medwiz.medwizdoctor.util
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider

import java.io.*


/**
 * @Author: Prithwiraj Nath
 * @Date:06/03/23
 */
object FileUtils {
    @Throws(IOException::class)
    fun createImageFile(mContext: Context): Uri {

        val tmpFile = File.createTempFile(
            System.currentTimeMillis().toString(),
            ".jpg",
            mContext.getExternalFilesDir(null)
        ).apply {
            createNewFile()
            deleteOnExit()
        }


        return FileProvider.getUriForFile(mContext, "com.medwiz.doctor.provider", tmpFile)

    }

    fun saveFileInCache(context: Context?, imageBitmap: Bitmap, fileName: String?): File? {
        val cacheFile: File =
            getTempFile(context!!, fileName)!!
        try {

            val os: OutputStream = BufferedOutputStream(FileOutputStream(cacheFile))
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, os)

            os.flush()
            os.close()
            return cacheFile
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getTempFile(context: Context, fileName: String?): File? {
        var file: File? = null
        try {
            file =
                File.createTempFile(System.currentTimeMillis().toString(), ".jpg", context.cacheDir)
        } catch (e: IOException) {
            // Error while creating file
        }
        return file

    }

}