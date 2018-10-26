package ge.wololo.ioane.fractarly.utils

import android.graphics.Bitmap


typealias Pixel = Pair<Int, Int>

/**
 * Creates iterator for Bitmap Pixels.
 */
fun Bitmap.iterator(): Iterator<Pixel> {
    return object : Iterator<Pixel> {
        var currIdx = 0
        var count = width * height

        override fun hasNext(): Boolean = currIdx < count

        override fun next(): Pixel = (currIdx % width to currIdx / width).also { currIdx++ }
    }
}

typealias Coordinates = Pair<Float, Float>

/**
 * Computes, which Coordinate is represented by [this] Pixel
 * @param anchor is a 0,0 Pixel coordinate, so that we can compute current pixel coordinates
 * @param scale this is, how many pixels make single unit on coordinates space.
 */
fun Pixel.toCoordinates(anchor: Coordinates, scale: Float): Coordinates {
    val (x, y) = this
    val (aX, aY) = anchor
    return aX + (x / scale) to aY - (y / scale)
}