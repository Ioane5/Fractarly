package ge.wololo.ioane.fractarly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ge.wololo.ioane.fractarly.algorithms.createMandelbrotBitmap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateFractal()
    }

    // TODO Move this in ViewModel, no coroutines should be exposed in Activity
    fun generateFractal() {
        uiScope.launch {
            fractalView.setImageBitmap(createMandelbrotBitmap())
        }
    }

    private val uiScope = CoroutineScope(Dispatchers.Main)
}
