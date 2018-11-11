package ge.wololo.ioane.fractarly.widgets.fractal

import android.graphics.Bitmap
import android.util.Size
import androidx.core.graphics.set
import ge.wololo.ioane.fractarly.utils.Coordinates
import ge.wololo.ioane.fractarly.utils.iterator
import ge.wololo.ioane.fractarly.utils.toCoordinates
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Simple Mandelbrot Fractal Rendering
 */
class MandelbrotRenderer : FractalRenderer {

    override val minZoom: Float? = 0.5f

    override val maxZoom: Float? = 100f

    override var renderSize: Size = Size(1000, 1000)
        set(value) {
            field = value
            bitmap = Bitmap.createScaledBitmap(bitmap, renderSize.width, renderSize.height, false)
            render()
        }

    override var currentFocus: Coordinates = Coordinates(0f, 0f)
        set(value) {
            field = value
            render()
        }

    override val pixelPerUnit: Int = 250

    override var currentZoom: Float = 1f
        set(value) {
            field = value
            render()
        }

    private val _renderChannel = Channel<Bitmap>()
    // We only expose [ReceiveChannel]
    override val renderChannel: ReceiveChannel<Bitmap> get() = _renderChannel

    override fun changeBy(x: Float, y: Float, zoom: Float) {
        TODO()
    }

    private var bitmap: Bitmap = Bitmap.createBitmap(renderSize.width, renderSize.height, Bitmap.Config.ARGB_8888)

    private var renderJob: Job? = null

    private fun render() {
        renderJob?.cancel()
        renderJob = GlobalScope.launch {
            val anchorCoordinates = Coordinates(
                    x = currentFocus.x - (renderSize.width / 2f / pixelPerUnit / currentZoom),
                    y = currentFocus.y + (renderSize.height / 2f / pixelPerUnit / currentZoom)
            )
            val pixelPerUnit = pixelPerUnit.toFloat() * currentZoom

            var pixelCount = 0
            bitmap.iterator().forEach { pixel ->
                val coordinates = pixel.toCoordinates(anchorCoordinates, pixelPerUnit)
                val color = coordinates.toMandelbrotColor()
                val (x, y) = pixel
                // If canceled do not touch bitmap anymore
                if (!isActive) return@launch
                bitmap[x, y] = color
                // Send update while rendering some pixels
                if (++pixelCount % PIXEL_PER_UPDATE == 0) {
                    _renderChannel.send(bitmap)
                }
            }
            _renderChannel.send(bitmap)
        }
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

    companion object {
        /**
         * How many pixels should be drawn per update
         */
        const val PIXEL_PER_UPDATE = 5000
    }
}