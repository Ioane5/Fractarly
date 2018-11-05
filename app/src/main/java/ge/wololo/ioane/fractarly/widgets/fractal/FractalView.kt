package ge.wololo.ioane.fractarly.widgets.fractal

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(renderer.generate(), 0f, 0f, null)
    }

}