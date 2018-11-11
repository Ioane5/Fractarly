package ge.wololo.ioane.fractarly.utils

import android.graphics.Bitmap

data class Coordinates(val x: Float, val y: Float)
data class Pixel(val x: Int, val y: Int)

/**
 * Creates iterator for Bitmap Pixels.
 */
fun Bitmap.iterator(): Iterator<Pixel> {
    return object : Iterator<Pixel> {
        var currIdx = 0
        var count = width * height

        override fun hasNext(): Boolean = currIdx < count

        override fun next(): Pixel = Pixel(currIdx % width, currIdx / width).also { currIdx++ }
    }
}

/**
 * Computes, which Coordinate is represented by [this] Pixel
 * @param anchor is a 0,0 Pixel coordinate, so that we can compute current pixel coordinates
 * @param scale this is, how many pixels make single unit on coordinates space.
 */
fun Pixel.toCoordinates(anchor: Coordinates, scale: Float): Coordinates {
    val (x, y) = this
    val (aX, aY) = anchor
    return Coordinates(aX + (x / scale), aY - (y / scale))
}