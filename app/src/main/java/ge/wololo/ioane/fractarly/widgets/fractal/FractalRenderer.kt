package ge.wololo.ioane.fractarly.widgets.fractal

import android.graphics.Bitmap

/**
 * Fractal renderer is an interface that should be used to generate fractal as a Bitmap, so that
 * it's drawn on canvas.
 *
 * This just handles bitmap creation with specified elements.
 * Note that FractalRenderer might cache some data and provide fast render,
 * once some part is already rendered.
 *
 * TODO async support
 */
interface FractalRenderer {

    // TODO add color options initialization

    /**
     * TODO add size params, Zoom,
     */
    fun generate(width: Int, height: Int): Bitmap
}