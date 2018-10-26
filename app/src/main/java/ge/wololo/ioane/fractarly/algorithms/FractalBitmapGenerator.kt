package ge.wololo.ioane.fractarly.algorithms

import android.graphics.Bitmap
import androidx.core.graphics.set
import ge.wololo.ioane.fractarly.utils.iterator
import ge.wololo.ioane.fractarly.utils.toCoordinates
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// TODO move this in Separate file
suspend fun createMandelbrotBitmap(): Bitmap {
    return suspendCoroutine { continuation ->
        val width = 400
        val height = 400
        // Defines how many pixels make 1 cm in scale
        val pixelPerCentimeter = 100f
        // Top left Pixel on our coordinate
        val anchorCoordinates = -2f to 1f
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        bitmap.iterator().forEach { pixel ->
            val coordinates = pixel.toCoordinates(anchorCoordinates, pixelPerCentimeter)
            val color = coordinates.toMandelbrotColor()
            val (x, y) = pixel
            bitmap[x, y] = color
        }
        continuation.resume(bitmap)
    }
}