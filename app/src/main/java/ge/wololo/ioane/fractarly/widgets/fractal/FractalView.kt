package ge.wololo.ioane.fractarly.widgets.fractal

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Size
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * View, that knows how to draw a fractal.
 * It supports zooming/moving of a fractal from User Gestures
 */
class FractalView(context: Context, attr: AttributeSet) : View(context, attr) {

    var renderer: FractalRenderer = MandelbrotRenderer()
        set(value) {
            field = value
            invalidate()
        }

    private var fractalWidth: Int = 0
    private var fractalHeight: Int = 0
    private var leftPadding: Float = 0f
    private var topPadding: Float = 0f

    private var fractalBitmap: Bitmap? = null

    init {
        GlobalScope.launch(Dispatchers.Main) {
            renderer.renderChannel.consumeEach {
                fractalBitmap = it
                invalidate()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fractalWidth = w - (paddingLeft + paddingRight)
        fractalHeight = h - (paddingTop + paddingBottom)
        leftPadding = paddingLeft.toFloat()
        topPadding = paddingTop.toFloat()
        renderer.renderSize = Size(fractalWidth, fractalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        fractalBitmap?.let {
            canvas.drawBitmap(it, leftPadding, topPadding, null)
        }
    }

    /**
     * Fractal current domain (
     */
    //    private val currentViewport = RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX)

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            renderer.currentZoom *= detector.scaleFactor
            return true
        }
    }

    private val scaleDetector = ScaleGestureDetector(context, scaleListener)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return scaleDetector.onTouchEvent(event)
                || super.onTouchEvent(event)
    }

}