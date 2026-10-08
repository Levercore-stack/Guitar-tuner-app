package com.levercore.guitartuner.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Lighter graphite / steel-blue. Preserve readable pitch-state colors.
val TunerBackground=Color(0xFF263A49)
val TunerPanel=Color(0xFF354E5E)
val TunerNeck=Color(0xFF222B34)
val TunerNeckFrame=Color(0xFF30404D)
val TunerNeckBorder=Color(0xFF7893A6)
val TunerTuningPanel=Color(0xFF45647A)
val TunerTuningBorder=Color(0xFF779CAF)
val TunerPresetTile=Color(0xFF2F4A5E)
val TunerSelectedPreset=Color(0xFF233B4B)
val TunerGold=Color(0xFFF3D38C)
val TunerSelection=Color(0xFF486B7C)
val TunerOutline=Color(0xFF7893A4)
val TunerAccent=Color(0xFFC6E6F2)
val TunerGreen=Color(0xFFA3E8C6)
val TunerFlat=Color(0xFFFFA3AE)
val TunerSharp=Color(0xFFF6D28F)
private val colors=darkColorScheme(
    primary=TunerAccent,onPrimary=TunerBackground,secondary=TunerGreen,
    background=TunerBackground,surface=TunerPanel,surfaceVariant=TunerSelection,
    onSurface=Color(0xFFFFFFFF),onBackground=Color(0xFFFFFFFF),
    onSurfaceVariant=Color(0xFFD7E2EA),outline=TunerOutline,error=TunerFlat
)
private val shapes=Shapes(small=RoundedCornerShape(10.dp),medium=RoundedCornerShape(14.dp),large=RoundedCornerShape(20.dp))
@Composable fun TunerTheme(content:@Composable ()->Unit){
    MaterialTheme(colorScheme=colors,shapes=shapes,content=content)
}
