package com.pop.components.animation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

/**
 * Utility class for converting Bitmaps to/from ByteArrays for database storage.
 */
object BitmapConverter {

    /**
     * Converts a Bitmap to ByteArray using compression.
     *
     * @param bitmap The bitmap to convert
     * @param format Compression format (default: PNG for lossless quality)
     * @param quality Compression quality (0-100, only used for JPEG)
     * @return ByteArray representation of the bitmap
     */
    fun bitmapToByteArray(
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
        quality: Int = 100
    ): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(format, quality, outputStream)
        return outputStream.toByteArray()
    }

    /**
     * Converts a ByteArray back to a Bitmap.
     *
     * @param bytes The byte array to decode
     * @return Bitmap decoded from the byte array, or null if decoding fails
     */
    fun byteArrayToBitmap(bytes: ByteArray): Bitmap? {
        if (bytes.isEmpty()) {
            return null
        }
        return try {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            null
        }
    }
}


