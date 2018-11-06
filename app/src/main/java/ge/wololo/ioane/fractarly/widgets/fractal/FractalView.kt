package ge.wololo.ioane.fractarly.widgets.fractal

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import timber.log.Timber

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

    var zoom = 0f
        set(value) {
            field = value
            invalidate()
        }

    var fractalWidth: Int = 0
    var fractalHeight: Int = 0
    var leftPadding: Float = 0f
    var topPadding: Float = 0f


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fractalWidth = w - (paddingLeft + paddingRight)
        fractalHeight = h - (paddingTop + paddingBottom)
        leftPadding = paddingLeft.toFloat()
        topPadding = paddingTop.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Timber.d("onDraw: $fractalWidth $fractalHeight")
        canvas.drawBitmap(renderer.generate(fractalWidth, fractalHeight), leftPadding, topPadding, null)
    }

}