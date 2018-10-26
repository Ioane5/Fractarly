package ge.wololo.ioane.fractarly.algorithms

import ge.wololo.ioane.fractarly.utils.Coordinates

/**
 * Mandelbrot formula is
 * f(z) = z^2 + c
 * where c is a complex number, represented by our coordinates
 * So c = (x + iy)
 */
fun Coordinates.toMandelbrotColor(): Int {
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
    return if(currIteration > 25) android.graphics.Color.BLACK else android.graphics.Color.WHITE
}