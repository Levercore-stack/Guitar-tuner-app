package com.levercore.guitartuner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.levercore.guitartuner.ui.TunerTheme
import com.levercore.guitartuner.ui.TunerScreen

/** Android entrypoint; UI is isolated in ui/TunerScreen.kt. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TunerTheme { TunerScreen() } }
    }
}
