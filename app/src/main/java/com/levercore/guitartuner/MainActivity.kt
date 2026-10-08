package com.levercore.guitartuner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.levercore.guitartuner.ui.TunerTheme
import com.levercore.guitartuner.ui.TunerScreen

/** Android entrypoint; UI is isolated in ui/TunerScreen.kt. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Draw our blank safe-area background behind Android system status/navigation bars.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
        setContent { TunerTheme { TunerScreen() } }
    }
}
