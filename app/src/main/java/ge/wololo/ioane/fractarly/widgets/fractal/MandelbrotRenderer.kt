package ge.wololo.ioane.fractarly.widgets.fractal

import android.graphics.Bitmap
import androidx.core.graphics.set
import ge.wololo.ioane.fractarly.utils.Coordinates
import ge.wololo.ioane.fractarly.utils.iterator
import ge.wololo.ioane.fractarly.utils.toCoordinates
import kotlin.math.floor


/**
 * Simple Mandelbrot Fractal Rendering
 */
class MandelbrotRenderer : FractalRenderer {

    // Top left Pixel on our coordinate
    // This value should be modified on Zoom and Drag
    private var anchorCoordinates: Coordinates = -2f to 1f
    // Fractal Width in Coordinate System units
    // Note that this value should be modified on Zoom
    private var widthSize = 3f

    override fun generate(width: Int, height: Int): Bitmap {
        val pixelPerUnit = floor(width / widthSize)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.iterator().forEach { pixel ->
            val coordinates = pixel.toCoordinates(anchorCoordinates, pixelPerUnit)
            val color = coordinates.toMandelbrotColor()
            val (x, y) = pixel
            bitmap[x, y] = color
        }
        return bitmap
    }

    /**
     * Mandelbrot formula is
     * f(z) = z^2 + c
     * where c is a complex number, represented by our coordinates
     * So c = (x + iy)
     */
    private fun Coordinates.toMandelbrotColor(): Int {
        val maxIteration = 50
        var currIteration = 0
        // c
        val (cx, cy) = this
        // Z
        var (zx, zy) = 0f to 0f
        while (zx * zx + zy * zy < 2 && currIteration < maxIteration) {
            val nextZx = zx * zx - zy * zy + cx
            val nextZy = 2 * zx * zy + cy
            zx = nextZx
            zy = nextZy
            currIteration++
        }
        // TODO make better color transformation
        return if (currIteration > 25) android.graphics.Color.BLACK else android.graphics.Color.WHITE
    }
}